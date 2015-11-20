(ns mech.generator
  (:require [mech.util :as u]
            [mech.image :as i]
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

(defn corp-gen [specs]
  (let [f (rand-nth [#(corp-proper-name)
                     #(u/join-with-spaces
                       (corp-proper-name)
                       (u/join-without-spaces (corp-name-prefix) (corp-name-suffix)))
                     #(u/join-with-spaces (corp-proper-name) (corp-name-suffix-standalone))])]
    (assoc specs :manufacturer (f))))

(def mech-classes ["Heavy" "Light" "Medium"])

(def mech-vehicle-type-keys [:rig])

(def mech-vehicle-descriptions {:rig (rand-nth ["General Purpose Rig" "Scout/Recon Rig"])})

(defn mech-vehicle-description [specs]
  (let [key (rand-nth mech-vehicle-type-keys)]
    (assoc specs :description (key mech-vehicle-descriptions))))

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

(defn mech-vehicle-params [specs]
  (let [key (rand-nth mech-vehicle-type-keys)
        height ((key mech-vehicle-heights))
        horse-power (+ (u/rand-float -100 100) (* height 100))
        power (* (rand-nth [2 3 4 5]) horse-power)]
    (merge
     specs
     {:height (goog.string.format
               "%.1f m"
               height)
      :weight (goog.string.format
               "%.0f tonnes"
               (* height ((key mech-vehicle-weight-ratios))))
      :horse-power (goog.string.format
                    "%.0f hp"
                    horse-power)
      :power (goog.string.format
              "%s-%.0f%s"
              (rand-nth u/letters-upper-case)
              power
              (rand-nth u/alphanumeric-upper-case))
      :primary-movement (goog.string.format
                         "%s (%.0f kph)"
                         (:primary-movement
                          (key mech-vehicle-movement-types))
                         (+
                          (/ horse-power 10)
                          ((:primary-movement
                            (key mech-vehicle-movement-modifiers)))))
      :secondary-movement (goog.string.format
                           "%s (%.0f kph)"
                           (:secondary-movement
                            (key mech-vehicle-movement-types))
                           (+
                            (/ horse-power 10)
                            ((:secondary-movement
                              (key mech-vehicle-movement-modifiers)))))})))

(def mech-types ["Assault" "Combat" "Aerospace" "Urban Combat" "Construction" "General Purpose"])

(def mech-manufacturer-codes ["UCS" "ONION" "FCS" "WACS" "WSSA" "OBSF" "ACSF" "NCS" "VFK" "EPAN" "VCS" "HAAS"])

(def mech-serial-number-length [1 2])

(defn mech-production-code [specs]
  (let [manufacturer (apply str (take 4 (repeatedly #(rand-nth u/letters-upper-case))))
        serial-l (rand-nth mech-serial-number-length)
        serial (apply str (take serial-l (repeatedly #(rand-nth u/numbers))))
        series (apply str (take 2 (repeatedly #(rand-nth u/letters-upper-case))))
        purpose (apply str (take 3 (repeatedly #(rand-nth u/letters-upper-case))))]
    (assoc specs :production-code (goog.string.format "%s-%s%s-%s"
                                                      manufacturer
                                                      serial
                                                      series
                                                      purpose))))

(def mech-production-types ["Mass Production" "Limited Production" "Experimental"])

(defn mech-production-type [specs]
  (assoc specs :production-type (rand-nth mech-production-types)))

(def default-weapon-prefixes ["A" "M" "P" "E" "X"])

(defn mech-weapon-types [] [{:type :lmg
                             :label "Light Machine Gun"
                             :prefixes default-weapon-prefixes
                             :ammo-multiplier 150}
                            {:type :smg
                             :label "Submachine Gun"
                             :prefixes default-weapon-prefixes
                             :ammo-multiplier 150}
                            {:type :hmg
                             :label "Heavy Machine Gun"
                             :prefixes default-weapon-prefixes
                             :ammo-multiplier 150}
                            {:type :sg
                             :label "Shotgun"
                             :prefixes default-weapon-prefixes
                             :ammo-multiplier 1.5}
                            {:type :pbr
                             :label "Pillbox Rocket Launcher"
                             :prefixes ["P"]
                             :ammo-multiplier 3}
                            {:type :gl
                             :label "Grenade Launcher"
                             :prefixes ["M" "P"]
                             :ammo-multiplier 3}
                            {:type :cs
                             :label "Chainsword"
                             :prefixes (conj default-weapon-prefixes "C")
                             :ammo-multiplier 0}
                            {:type :rg
                             :label "Railgun"
                             :prefixes default-weapon-prefixes
                             :ammo-multiplier 1}
                            {:type :cg
                             :label "Chaingun"
                             :prefixes (conj default-weapon-prefixes "C")
                             :ammo-multiplier 150}])

(def mech-hardpoint-weapon-types {:rig
                                  {:head []
                                   :torso [:pbr :rg]
                                   :lt-arm [:cg :lmg :hmg :sg :gl :cs :smg]
                                   :lt-shoulder [:pbr :gl :rg]
                                   :rt-arm [:cg :lmg :hmg :sg :gl :cs :smg]
                                   :rt-shoulder [:pbr :gl :rg]
                                   :legs []}})

(enable-console-print!)

(def mech-weapon-hardpoint-types
  {:pbr #{:lt-shoulder :rt-shoulder}
   :rg #{:torso :lt-shoulder :rt-shoulder}
   :cg #{:lt-arm :rt-arm}
   :lmg #{:lt-arm :rt-arm}
   :hmg #{:lt-arm :rt-arm}
   :sg #{:lt-arm :rt-arm}
   :gl #{:lt-arm :rt-arm :lt-shoulder :rt-shoulder}
   :cs #{:lt-arm :rt-arm}
   :smg #{:lt-arm :rt-arm}})

(def weapon-mounting-points (set [:torso
                                  :lt-arm
                                  :lt-shoulder
                                  :rt-arm
                                  :rt-shoulder]))

(def mech-hardpoint-labels {:head "Head"
                            :torso "Torso"
                            :lt-arm "Left Hand"
                            :lt-shoulder "Left Shoulder"
                            :rt-arm "Right Hand"
                            :rt-shoulder "Right Shoulder"
                            :legs "Legs"})

(def mech-weapon-base-number [2 4 6 8 16 32])

(let [poss #{:lt-arm :rt-arm}
      available #{:torso :lt-arm :lt-shoulder :rt-shoulder}]
  (filter poss available))

(defn mech-weapon [specs]
  (let [w (rand-nth (mech-weapon-types))
        base-number (rand-nth mech-weapon-base-number)
        base-label (:label w)
        prefix (rand-nth (:prefixes w))
        manufacturer (rand-nth mech-manufacturer-codes)
        ammo (* (:ammo-multiplier w) base-number)
        f (rand-nth [+ -])
        prefix-number (Math/abs (f (* ammo (* (rand-int 2) base-number)) (rand-int 20)))
        loadout (u/join-with-spaces ammo "rounds")
        hardpoint (rand-nth
                   (seq (filter (get mech-weapon-hardpoint-types (:type w))
                                (:mounting-points specs))))
        hardpoint-map {:hardpoint hardpoint
                       :label (hardpoint mech-hardpoint-labels)}]
    (-> specs
        (assoc :weapons
               (conj
                (:weapons specs)
                (-> w
                    (assoc :loadout loadout)
                    (assoc :hardpoint hardpoint-map)
                    (assoc :ammo ammo)
                    (assoc :label (goog.string.format "%s %s-%.0f %s" manufacturer prefix prefix-number base-label)))))
        (assoc :mounting-points (disj (:mounting-points specs) hardpoint)))))

(defn mech-base-specs []
  {:image (i/mech-image)
   :code-name (u/capitalize-words (rand-nth u/animal-names))
   :mounting-points weapon-mounting-points
   :weapons []})

(defn mech-specifications []
  (let [specs (mech-base-specs)]
    (-> specs
        mech-production-code
        mech-production-type
        corp-gen
        mech-vehicle-description
        mech-vehicle-params
        mech-vehicle-params
        mech-weapon
        mech-weapon)))
