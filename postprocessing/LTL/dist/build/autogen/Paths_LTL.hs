module Paths_LTL (
    version,
    getBinDir, getLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []
bindir, libdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "/home/osboxes/.cabal/bin"
libdir     = "/home/osboxes/.cabal/lib/x86_64-linux-ghc-7.10.3/LTL-0.1.0.0-CIxt346zNuk8F1yjtCM2TW"
datadir    = "/home/osboxes/.cabal/share/x86_64-linux-ghc-7.10.3/LTL-0.1.0.0"
libexecdir = "/home/osboxes/.cabal/libexec"
sysconfdir = "/home/osboxes/.cabal/etc"

getBinDir, getLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "LTL_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "LTL_libdir") (\_ -> return libdir)
getDataDir = catchIO (getEnv "LTL_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "LTL_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "LTL_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
