(ns mech.generator
  (:require [mech.util :as u]
            [clojure.string]
            [goog.string.format]))

(defn corp-name-prefix []
  (rand-nth ["Nano" "Micro" "Exa" "Geo" "Bio" "Neuro" "Omni" "Tera" "Mega" "Techno" "Pharma" "Mono" "Poly" "Syllo" "Holo" "Para" "Proto" "Pico" "Tetra" "Arco" "Thyla" "Sino" "Neo" "Multi" "Tele" "Dyna" "Hashi"]))

(defn corp-name-suffix []
  (rand-nth ["systems" "soft" "links" "corp" "logies" "ceuticals" "dyne" "gistics" "chron" "synth" "works" "tron" "tech" "net" "tronics" "ptics" "national" "trace"]))

(defn corp-name-suffix-standalone []
  (rand-nth ["Systems" "Ltd." "GmbH" "Incorporated" "Industries" "Heavy Industries" "Digital" "Omnium" "Logistics" "Control" "Dynamics" "Fiberoptics" "Holdings" "& Co." "Cybertronics" "Transorbital" "Co., Ltd."]))

(defn corp-proper-name []
  (rand-nth ["Haas" "Maas" "Aztlan" "Fuchi" "Ares" "Olympic" "Integra" "Arkady" "Panafrican" "Panamerican" "Eternium" "MIRAI" "Lucent" "Lucid" "United" "Orbital" "Chyron" "Fury" "Ayanda" "MOCO" "Monad" "Talismyn" "NERV" "Eigen" "Solidus" "Animus" "Tsukiji" "Wei" "Macron" "Hua" "Moto" "Huaneng" "Sinopec" "Cheung" "Wuhan" "Li" "Fung" "Esprit" "Wheelock" "DaTang" "Maanshan" "Shenzhen" "SAIC" "Yanzhou" "Kweichow" "Moutai" "Yibin" "Bohr" "Pagani" "CAPSULE" "aurora" "BNC" "Saito" "WuXing" "Shinra" "Oesterheim" "Maersk" "Atari" "Renraku" "Matsuhama" "Shimago-Domínguez" "Gibson" "Mitsubishi" "Findley" "Shiawase" "Alphabet" "Maxwell"]))

(defn corp-gen []
  (let [f (rand-nth [#(corp-proper-name)
                     #(u/join-with-spaces
                       (corp-proper-name)
                       (u/join-without-spaces (corp-name-prefix) (corp-name-suffix)))
                     #(u/join-with-spaces (corp-proper-name) (corp-name-suffix-standalone))])]
    (f)))

(def mech-classes ["Heavy" "Light" "Medium"])

(def mech-vehicle-type-keys [:rig])

(def mech-vehicle-descriptions {:rig (rand-nth ["General Purpose Rig" "Scout/Recon Rig"])})

(defn mech-vehicle-description []
  (let [key (rand-nth mech-vehicle-type-keys)]
    (key mech-vehicle-descriptions)))

(def mech-vehicle-heights {:rig #(u/rand-float 3.7 5.5)})

(def mech-vehicle-weight-ratios {:rig #(u/rand-float (/ 1 0.45) (/ 1  0.75))})

(def mech-vehicle-movement-modifiers
  {:rig
   {:primary-movement #(u/rand-float -10 10)
    :secondary-movement #(u/rand-float 15 30)}})

(def mech-vehicle-movement-types
  {:rig
   {:primary-movement "Walk"
    :secondary-movement "Rolling"}})

(defn mech-vehicle-params []
  (let [key (rand-nth mech-vehicle-type-keys)
        height ((key mech-vehicle-heights))
        power (+ (u/rand-float -100 100) (* height 100))]
    {:height (goog.string.format "%.1f m" height)
     :weight (goog.string.format "%.0f tonnes"
                      (* height ((key mech-vehicle-weight-ratios))))
     :power (goog.string.format "%.0f hp"
                     power)
     :primary-movement (goog.string.format "%s (%.0f kph)"
                                (:primary-movement
                                 (key mech-vehicle-movement-types))
                                (+
                                 (/ power 10)
                                 ((:primary-movement
                                   (key mech-vehicle-movement-modifiers)))))
     :secondary-movement (goog.string.format "%s (%.0f kph)"
                                  (:secondary-movement
                                   (key mech-vehicle-movement-types))
                                  (+
                                   (/ power 10)
                                   ((:secondary-movement
                                     (key mech-vehicle-movement-modifiers)))))}))

(def mech-types ["Assault" "Combat" "Aerospace" "Urban Combat" "Construction" "General Purpose"])

(def mech-manufacturer-codes ["UCS" "ONION" "FCS" "WACS" "WSSA" "OBSF" "ACSF" "NCS" "VFK" "EPAN" "VCS"])

(def mech-serial-number-length [1 2])

(defn mech-production-code []
  (let [manufacturer (apply str (take 4 (repeatedly #(rand-nth u/letters-upper-case))))
        serial-l (rand-nth mech-serial-number-length)
        serial (apply str (take serial-l (repeatedly #(rand-nth u/numbers))))
        series (apply str (take 2 (repeatedly #(rand-nth u/letters-upper-case))))
        purpose (apply str (take 3 (repeatedly #(rand-nth u/letters-upper-case))))]
    (goog.string.format "%s-%s%s-%s"
             manufacturer
             serial
             series
             purpose)))

(def mech-production-types ["Mass Production" "Limited Production" "Experimental"])

(defn mech-production-type [] (rand-nth mech-production-types))

(def mech-hardpoint-types {:rig
                           {:head []
                            :torso []
                            :lt-arm []
                            :lt-shoulder []
                            :rt-arm []
                            :rt-shoulder []
                            :legs []}})

 (def mech-placeholder-image "//placekitten.com/g/480/640")

(defn mech-specifications []
  (let [image mech-placeholder-image
        code-name (clojure.string/capitalize (rand-nth u/animal-names))
        production-code (mech-production-code)
        production-type (mech-production-type)
        manufacturer (corp-gen)
        description (mech-vehicle-description)
        {height :height
         weight :weight
         power :power
         primary-movement :primary-movement
         secondary-movement :secondary-movement} (mech-vehicle-params)]
    {:image image
     :code-name code-name
     :production-code production-code
     :production-type production-type
     :manufacturer manufacturer
     :description description
     :height height
     :weight weight
     :power power
     :horse-power power
     :primary-movement primary-movement
     :secondary-movement secondary-movement}))
