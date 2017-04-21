import Control.Monad
import Data.Either
import Data.List
import Data.Set (Set)
import qualified Data.Set as Set
import Data.Map.Strict (Map)
import qualified Data.Map.Strict as Map
import Data.Maybe (fromJust)
import Text.Parsec hiding (State, Error)
import Text.Parsec.String
import qualified Text.Parsec.Token as P
import Text.Parsec.Expr
import Text.Parsec.Language (emptyDef)

-- State is just an String
type State = String

-- Input / Output are Integers (in the C version of the problems)
-- Inputs are off by one (0 -> A), outputs are ok (1 -> A)
-- Error states are always integers
type Input = Integer
data Output = Output Integer | Invalid Integer | Error Integer
    deriving (Eq, Ord, Read, Show)
-- Multiple outputs are possible, although it only happens with Error states
type Outputs = [Output]

-- Transition in Dot file is a simple tuple
type Trans = (State, Input, State, Outputs)

-- For NuSMV we convert the ints to their original meaning (Characters)
-- Note that A is an invalid enum value in NuSMV (dunno why, 'B' is fine)
-- so we prepend a little bit
type NuInput = String
type NuOutput = String

toAlphI :: Input -> NuInput
toAlphI i = 'i' : [cycle ['A'..'Z'] !! fromInteger i]

toAlphO :: Output -> NuOutput
toAlphO (Output i)  = 'o' : [cycle ['A'..'Z'] !! fromInteger (i-1)]
toAlphO (Invalid i) = "oInv"
toAlphO _ = error "cannot convert output"

getStates :: [Trans] -> Set State
getStates l = Set.fromList (froms ++ tos) where
    froms = map (\(a,_,_,_) -> a) l
    tos   = map (\(_,_,a,_) -> a) l

getInitial :: [Trans] -> State
getInitial ((a,_,_,_):_) = a
getInitial _ = error "empty state space"

getInputs :: [Trans] -> Set NuInput
getInputs l = Set.fromList . map (\(_,a,_,_) -> toAlphI a) $ l

getOutputs :: [Trans] -> Set NuInput
getOutputs l = Set.fromList . map (\(_,_,_,a) -> toAlphO (head a)) $ l

-- doesn't check for duplicates, we know Learnlib generates a deterministic thing
toMap :: [Trans] -> Map (State, NuInput) (State, NuOutput)
toMap trans = base where
    base = Map.fromList . map (\(from, i, to, o) -> ((from, toAlphI i), (to, toAlphO (head o)))) $ trans

isValid :: Map (State, NuInput) (State, NuOutput) -> (State, NuInput) -> Bool
isValid system p = case Map.lookup p system of
    Nothing -> False
    Just (_, "oInv") -> False
    Just (_, ('o':_)) -> True
    _ -> False

main = do
    file <- lines <$> getContents
    let trans = parseDot file
    let states  = Set.toList $ getStates trans
    let inputs  = Set.toList $ getInputs trans
    let outputs = case length inputs of
            5  -> ["oInv","oS","oT","oU","oV","oW","oX","oY","oZ"]
            10 -> ["oInv","oO","oP","oQ","oR","oS","oT","oU","oV","oW","oX","oY","oZ"]
            20 -> ["oInv","oU","oV","oW","oX","oY","oZ"]
            _  -> error "not a valid number of inputs"
    putStrLn $ "MODULE main"
    putStrLn $ "VAR"

    let printSet f s = mconcat . intersperse ", " . map f $ s
    putStrLn $ "    state  : {" ++ printSet id states    ++ "};"
    putStrLn $ "    input  : {" ++ printSet id inputs  ++ ", null};"
    putStrLn $ "    output : {" ++ printSet id outputs ++ ", null};"

    let init = getInitial trans
    putStrLn $ "ASSIGN"
    putStrLn $ "    init(state)  := " ++  init  ++ ";"
    putStrLn $ "    init(output) := " ++ "null" ++ ";"

    let system = toMap trans
    let si = (,) <$> states <*> inputs
    let (fsi, deadsi) = partition (isValid system) si
    let printCond (s, i) = "state = " ++ s ++ " & input = " ++ i ++ " : "
    let printCase f psi = putStrLn $ "        " ++ printCond psi ++ f (fromJust (Map.lookup psi system)) ++ ";"
    putStrLn $ "    next(state)  := case"
    forM_ si (printCase fst)
    putStrLn $ "        input = null : state;"
    putStrLn $ "    esac;"
    putStrLn $ "    next(output) := case"
    forM_ si (printCase snd)
    putStrLn $ "        input = null : null;"
    putStrLn $ "    esac;"

    let printInvar (s, i) = putStrLn $ "    & !(state = " ++ s ++ " & input = " ++ i ++ ")"
    putStrLn $ "INVAR !(input = null & output = null)"
    putStrLn $ "    & !(input != null & output != null)"
    forM_ deadsi printInvar


{-

MODULE main
VAR
    state : {s1, s2};
    input : {a, B, null};
    output : {x, y, null};
ASSIGN
    init(state)  := s1;
    init(output) := null;
    next(state)  := case
        state = s1 & input = a : s2;
        state = s2 & input = a : s1;
        state = s1 & input = B : s2;
        state = s2 & input = B : s2;
        input = null : state;
    esac;
    next(output)  := case
        state = s1 & input = a : x;
        state = s2 & input = a : y;
        state = s1 & input = B : x;
        state = s2 & input = B : x;
        input = null : null;
    esac;
INVAR !(input = null & output = null)
    & !(input != null & output != null)


-}

-- Parser for Dot file (as generated by my experiments)
-- This is not a full fledged Dot parser of course
-- And it ignore lines it cannot read
lexer        = P.makeTokenParser $ emptyDef
reservedOp   = P.reservedOp lexer
reserved     = P.reserved lexer
identifier   = P.identifier lexer
brackets     = P.brackets lexer
semi         = P.semi lexer
natural      = P.natural lexer

parseTrans :: Parser Trans
parseTrans = do
    spaces
    from <- identifier
    reservedOp "->"
    to <- identifier
    (i, o) <- brackets parseLabel
    optional semi
    return (from, i, to, o)

parseLabel :: Parser (Input, Outputs)
parseLabel = do
    string "label=\""
    i <- natural
    reservedOp "/"
    o <- parseOutput
    string "\""
    return (i, o)

parseOutput :: Parser Outputs
parseOutput = do
    sepEndBy1
        ( Output <$> natural
            <|> Invalid <$> (reserved "Invalid input:" *> natural)
            <|> Error <$> (reserved "ERROR" *> natural)
        ) semi

parseDot :: [String] -> [Trans]
parseDot lines = rights . map (parse parseTrans "") $ lines
