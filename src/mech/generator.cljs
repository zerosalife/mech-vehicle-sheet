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
        horse-power (+ (u/rand-float -100 100) (* height 100))
        power (* (rand-nth [2 3 4 5]) horse-power)]
    {:height (goog.string.format "%.1f m" height)
     :weight (goog.string.format "%.0f tonnes"
                                 (* height ((key mech-vehicle-weight-ratios))))
     :horse-power (goog.string.format "%.0f hp"
                                horse-power)
     :power (goog.string.format "%s-%.0f%s"
                                      (rand-nth u/letters-upper-case)
                                      power
                                      (rand-nth u/alphanumeric-upper-case))
     :primary-movement (goog.string.format "%s (%.0f kph)"
                                           (:primary-movement
                                            (key mech-vehicle-movement-types))
                                           (+
                                            (/ horse-power 10)
                                            ((:primary-movement
                                              (key mech-vehicle-movement-modifiers)))))
     :secondary-movement (goog.string.format "%s (%.0f kph)"
                                             (:secondary-movement
                                              (key mech-vehicle-movement-types))
                                             (+
                                              (/ horse-power 10)
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
                             :ammo-multiplier 10}
                            {:type :cg
                             :label "Chaingun"
                             :prefixes (conj default-weapon-prefixes "C")
                             :ammo-multiplier 150}])

(def mech-hardpoint-weapon-types {:rig
                                  {:head []
                                   :torso [:pbr]
                                   :lt-arm [:cg :lmg :hmg :sg :gl :cs :smg]
                                   :lt-shoulder [:pbr :gl]
                                   :rt-arm [:cg :lmg :hmg :sg :gl :cs :smg]
                                   :rt-shoulder [:pbr :gl]
                                   :legs []}})

(def weapon-mounting-points [:torso
                             :lt-arm
                             :lt-shoulder
                             :rt-arm
                             :rt-shoulder])

(def mech-hardpoint-labels {:head "Head"
                            :torso "Torso"
                            :lt-arm "Left Hand"
                            :lt-shoulder "Left Shoulder"
                            :rt-arm "Right Hand"
                            :rt-shoulder "Right Shoulder"
                            :legs "Legs"})

(def mech-weapon-base-number [2 4 6 8 16 32])

(defn mountable? [weapon hardpoint]
  (let [hardpoints (:rig mech-hardpoint-weapon-types)]
    (contains? (set (hardpoint hardpoints)) (:type weapon))))

(defn mech-hardpoint-weapon-mount [weapon]
  (let [hardpoint (rand-nth weapon-mounting-points)]
    (if (mountable? weapon hardpoint)
      {:hardpoint hardpoint
       :label (hardpoint mech-hardpoint-labels)}
      (mech-hardpoint-weapon-mount weapon))))

(defn mech-weapon []
  (let [w (rand-nth (mech-weapon-types))
        base-number (rand-nth mech-weapon-base-number)
        base-label (:label w)
        prefix (rand-nth (:prefixes w))
        manufacturer (rand-nth mech-manufacturer-codes)
        ammo (* (:ammo-multiplier w) base-number)
        f (rand-nth [+ -])
        prefix-number (Math/abs (f (* ammo (* (rand-int 2) base-number)) (rand-int 20)))
        loadout (u/join-with-spaces ammo "rounds")
        hardpoint (mech-hardpoint-weapon-mount w)]
    (-> w
        (assoc :loadout loadout)
        (assoc :hardpoint hardpoint)
        (assoc :label (goog.string.format "%s %s-%.0f %s" manufacturer prefix prefix-number base-label)))))

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
         horse-power :horse-power
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
     :horse-power horse-power
     :primary-movement primary-movement
     :secondary-movement secondary-movement
     :weapons [(let [w (mech-weapon)]
                 {:label (:label w)
                  :hardpoint (:hardpoint w)
                  :loadout (:loadout w)})
               (let [w (mech-weapon)]
                 {:label (:label w)
                  :hardpoint (:hardpoint w)
                  :loadout (:loadout w)})]}))
