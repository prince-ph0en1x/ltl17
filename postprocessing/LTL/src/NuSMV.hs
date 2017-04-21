{-# LANGUAGE DeriveGeneric, DeriveAnyClass, DeriveFunctor #-}

module NuSMV where

import Data.Functor.Fixedpoint
import GHC.Generics (Generic)
import qualified LTL as L
import LTL (InOut(..), toIO)
import Text.Parsec

-- Only the fragment I need
data NuLTL0 lit expr
    = Literal lit
    | NLiteral lit
    | TRUE
    | FALSE
    | AND expr expr
    | OR expr expr
    | NOT expr
    | IMPLIES expr expr
    | X expr
    | G expr
    | F expr
    | U expr expr
    | V expr expr -- same a release
    deriving (Eq, Ord, Read, Show, Generic, Functor)

type NuLTL lit = Fix (NuLTL0 lit)

convert :: L.LTL lit -> NuLTL lit
convert = cata (Fix . alg) where
    alg (L.Literal lit) = Literal lit
    alg (L.TRUE) = TRUE
    alg (L.FALSE) = FALSE
    alg (L.AND x y) = AND x y 
    alg (L.OR x y) = OR x y
    alg (L.NOT x) = NOT x
    alg (L.F x) = F x
    alg (L.G x) = G x
    alg (L.X x) = X x
    alg (L.U x y) = U x y
    alg (L.WU x y) = V y (Fix $ OR x y)
    alg (L.R x y) = V x y

simplify :: NuLTL lit -> NuLTL lit
simplify = cata alg where
    alg (NOT (Fix (NOT x))) = x
    alg (NOT (Fix (Literal str))) = Fix $ NLiteral str
    alg (NOT (Fix (NLiteral str))) = Fix $ Literal str
    alg (OR (Fix (NOT x)) y) = Fix $ IMPLIES x y
    alg x = Fix x

-- Simple pretty printer, using the kind of same syntax as the RERS challenge
prettyPrint :: (Bool -> a -> String) -> NuLTL a -> String
prettyPrint show = cata alg where
    brackets s = "(" ++ s ++ ")"
    alg (Literal a)  = brackets $ show True a
    alg (NLiteral a) = brackets $ show False a
    alg TRUE        = "TRUE"
    alg FALSE       = "FALSE"
    alg (AND s1 s2) = brackets $ s1 ++ " & " ++ s2
    alg (OR s1 s2)  = brackets $ s1 ++ " | " ++ s2
    alg (IMPLIES s1 s2)  = brackets $ s1 ++ " -> " ++ s2
    alg (NOT s1)    = "! " ++ s1
    alg (F s)       = "F " ++ s
    alg (G s)       = "G " ++ s
    alg (X s)       = "X " ++ s
    alg (U s1 s2)   = brackets $ s1 ++ " U " ++ s2
    alg (V s1 s2)   = brackets $ s1 ++ " V " ++ s2

showIO :: Bool -> InOut String String -> String
showIO True (Input str) = "input = " ++ str
showIO False (Input str) = "input != " ++ str
showIO True (Output str) = "output = " ++ str
showIO False (Output str) = "output != " ++ str

toNuSMV = prettyPrint showIO . convert . toIO
simpl str = either show (toNuSMV) (parse L.parseLTL "" str)
