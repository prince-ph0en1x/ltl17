import qualified Data.Set as Set
import Data.Set (Set)
import Control.Monad (forM_)
import System.Environment (getArgs)

getn :: String -> Int
getn ('E':'R':'R':'O':'R':n) = read n

isIn = Set.member

main = do
    [arg] <- getArgs
    let problem = read arg :: Int
    file <- lines <$> getContents
    let ns = map getn file
    let errorStates = Set.fromList ns
    let printCase n = putStrLn $ show problem ++ ", error_" ++ show n ++ ", " ++ if n `isIn` errorStates then "true" else "false" 
    forM_ (Set.toList errorStates) printCase
