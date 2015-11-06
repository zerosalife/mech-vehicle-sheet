(ns mech.util
  (:require [clojure.string]))

(def ^:const letters-upper-case ["A" "B" "C" "D" "E" "F" "G" "H" "I" "J" "K" "L" "M" "N" "O" "P" "Q" "R" "S" "T" "U" "V" "W" "X" "Y" "Z"])

(def ^:const numbers ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"])

(def ^:const alphanumeric-upper-case (into [] (concat letters-upper-case numbers)))

(def ^:const animal-names ["aardvark" "alligator" "alpaca" "antelope" "ape" "armadillo" "baboon" "badger" "bat" "bear" "beaver" "bison" "boar" "buffalo" "bull" "camel" "canary" "capybara" "cat" "chameleon" "cheetah" "chimpanzee" "chinchilla" "chipmunk" "cougar" "cow" "coyote" "crocodile" "crow" "deer" "dingo" "dog" "donkey" "dromedary" "elephant" "elk" "ewe" "ferret" "finch" "fish" "fox" "frog" "gazelle" "gila monster" "giraffe" "gnu" "goat" "gopher" "gorilla" "grizzly bear" "ground hog" "guinea pig" "hamster" "hedgehog" "hippopotamus" "hog" "horse" "hyena" "ibex" "iguana" "impala" "jackal" "jaguar" "kangaroo" "koala" "lamb" "lemur" "leopard" "lion" "lizard" "llama" "lynx" "mandrill" "marmoset" "mink" "mole" "mongoose" "monkey" "moose" "mountain goat" "mouse" "mule" "muskrat" "mustang" "mynah bird" "newt" "ocelot" "opossum" "orangutan" "oryx" "otter" "ox" "panda" "panther" "parakeet" "parrot" "pig" "platypus" "polar bear" "porcupine" "porpoise" "prairie dog" "puma" "rabbit" "raccoon" "ram" "rat" "reindeer" "reptile" "rhinoceros" "salamander" "seal" "sheep" "shrew" "silver fox" "skunk" "sloth" "snake" "squirrel" "tapir" "tiger" "toad" "turtle" "walrus" "warthog" "weasel" "whale" "wildcat" "wolf" "wolverine" "wombat" "woodchuck" "yak" "zebra" "quetzal" "seahawk" "kraken" "emu" "echidna" "oarfish" "bee" "capybara" "tortoise" "flamingo" "octopus" "bok choi" "roadrunner"])

(defn remove-empty-strings [strs]
  (remove #(or (= % "")
               (= % nil))
          strs))

(defn join-with-spaces [& strs]
  (let [strs (remove-empty-strings strs)]
    (clojure.string/join " " (vec strs))))

(defn join-without-spaces [& strs]
  (let [strs (remove-empty-strings strs)]
    (clojure.string/join "" (vec strs))))

(defn join-with-hyphen [& strs]
  (let [strs (remove-empty-strings strs)]
    (clojure.string/join "-" (vec strs))))

(defn rand-float [min max]
  (+ min (rand (- max min))))
