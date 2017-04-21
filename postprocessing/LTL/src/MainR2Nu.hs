import NuSMV

import Control.Monad (when)

main = loop where
    loop = do
        l <- getLine
        let l2 = case l of
                        [] -> []
                        '#':str -> []
                        ltl -> "LTLSPEC " ++ simpl ltl
        when (not $ null l2) $ putStrLn l2
        loop
