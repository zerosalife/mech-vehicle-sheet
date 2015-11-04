(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [mech.system]))

(reloaded.repl/set-init! #'mech.system/create-system)
