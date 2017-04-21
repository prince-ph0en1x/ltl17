{-# LANGUAGE PatternSynonyms #-}
{-# LANGUAGE DeriveGeneric, DeriveAnyClass, DeriveFunctor #-}

module LTL where

import Data.Functor.Fixedpoint
import GHC.Generics (Generic)
import Text.Parsec
import Text.Parsec.String
import qualified Text.Parsec.Token as P
import Text.Parsec.Expr
import Text.Parsec.Language (emptyDef)

import Prelude hiding (not, and, or, until)

-- as defined by the RERS challenge
data LTL0 lit expr
    = Literal lit    -- Literal
    | TRUE           -- true
    | FALSE          -- false
    | AND expr expr  -- phi & psi
    | OR expr expr   -- phi | psi
    | NOT expr       -- ! phi
    | F expr         -- Fianlly: F phi
    | G expr         -- Globally: G phi
    | X expr         -- Next: X phi
    | U expr expr    -- Until: phi U psi
    | WU expr expr   -- Weak until: phi WU psi
    | R expr expr    -- Relear: phi R psi
    deriving (Eq, Ord, Read, Show, Generic, Functor)

type LTL lit = Fix (LTL0 lit)

literal a   = Fix $ Literal a
true        = Fix $ TRUE
false       = Fix $ FALSE
and x y     = Fix $ AND x y
or x y      = Fix $ OR x y
not x       = Fix $ NOT x
finally x   = Fix $ F x
globally x  = Fix $ G x
next x      = Fix $ X x
until x y   = Fix $ U x y
wuntil x y  = Fix $ WU x y
release x y = Fix $ R x y

data InOut i o = Input i | Output o
    deriving (Eq, Ord, Read, Show, Generic)

-- We can parse the literals to be input or output
toIO :: LTL String -> LTL (InOut String String)
toIO = hmap (litMap lit) where
    lit str@('i':_) = Input str
    lit str@('o':_) = Output str
    lit str = error $ "cannot parse " ++ str

-- Unfortunately we cannot derive Bifunctor yet, so I'll do it manually
litMap :: (a -> b) -> LTL0 a e -> LTL0 b e
litMap f (Literal l) = Literal (f l)
litMap f TRUE = TRUE
litMap f FALSE = FALSE
litMap f (AND e1 e2) = AND e1 e2
litMap f (OR e1 e2) = OR e1 e2
litMap f (NOT e) = NOT e
litMap f (F e) = F e
litMap f (G e) = G e
litMap f (X e) = X e
litMap f (U e1 e2) = U e1 e2
litMap f (WU e1 e2) = WU e1 e2
litMap f (R e1 e2) = R e1 e2


-- some basic bottom up rewriting
-- most interesting are the true U x = F x and false R x = G x rules
simplify :: LTL lit -> LTL lit
simplify = cata alg where
    alg (AND FTRUE s2) = s2
    alg (AND FFALSE _) = FFALSE
    alg (AND s1 FTRUE) = s1
    alg (AND _ FFALSE) = FFALSE
    alg (OR FTRUE _)   = FTRUE
    alg (OR FFALSE s2) = s2
    alg (OR _ FTRUE)   = FTRUE
    alg (OR s1 FFALSE) = s1
    alg (NOT FTRUE)    = FFALSE
    alg (NOT FFALSE)   = FTRUE
    alg (U FTRUE s2)   = Fix $ F s2
    alg (R FFALSE s2)  = Fix $ G s2
    -- On the rest we simplify only the sub terms
    alg x             = Fix x

pattern FTRUE = Fix TRUE
pattern FFALSE = Fix FALSE

-- Simple pretty printer, using the kind of same syntax as the RERS challenge
prettyPrint :: (a -> String) -> LTL a -> String
prettyPrint show = cata alg where
    brackets s = "(" ++ s ++ ")"
    alg (Literal a) = show a
    alg TRUE        = "true"
    alg FALSE       = "false"
    alg (AND s1 s2) = brackets $ s1 ++ " & " ++ s2
    alg (OR s1 s2)  = brackets $ s1 ++ " | " ++ s2
    alg (NOT s1)    = "! " ++ s1
    alg (F s)       = "F " ++ s
    alg (G s)       = "G " ++ s
    alg (X s)       = "X " ++ s
    alg (U s1 s2)   = brackets $ s1 ++ " U " ++ s2
    alg (WU s1 s2)  = brackets $ s1 ++ " WU " ++ s2
    alg (R s1 s2)   = brackets $ s1 ++ " R " ++ s2


-- Parser is defined below
-- It does not take into account precedence
-- All prefix operators have tighter binding.
lexer        = P.makeTokenParser $ emptyDef
parens       = P.parens lexer 
reservedOp   = P.reservedOp lexer
reserved     = P.reserved lexer
identifier   = P.identifier lexer

parseLTL :: Parser (LTL String)
parseLTL = buildExpressionParser table parseTerm where
    binaries = ["&" ==> and, "|" ==> or, "U" ==> until, "WU" ==> wuntil, "R" ==> release]
    prefixes = ["!" ==> not, "F" ==> finally, "G" ==> globally, "X" ==> next]
    table = [ [ Prefix (chained (choice [operate t f | (t, f) <- prefixes])) ]
            , [ Infix (operate t f) AssocLeft | (t, f) <- binaries]
            ]
    operate name fun = reservedOp name *> pure fun
    chained p = chainl1 p $ return (.)
    (==>) = (,)

parseTerm :: Parser (LTL String)
parseTerm = parens parseLTL
          <|> reserved "true" *> pure true
          <|> reserved "false" *> pure false
          <|> literal <$> identifier
