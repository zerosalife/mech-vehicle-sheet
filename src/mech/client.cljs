(ns mech.client
  (:require [quiescent.core :as q]
            [quiescent.dom :as d]
            [mech.generator :as g]))

(def mech (g/mech-specifications))

(q/defcomponent CodeName [mech]
  (d/div {:id "code-name" :className "entry"}
         (d/div {:className "label"}
                "Code Name")
         (d/div {:className "content"}
                (:code-name mech))))

(q/defcomponent ProductionCode [mech]
  (d/div {:id "production-code" :className "entry"}
         (d/div {:className "label"}
                "Production Code")
         (d/div {:className "content"}
                (:production-code mech))))

(q/defcomponent ProductionType [mech]
  (d/div {:id "production-type" :className "entry"}
         (d/div {:className "label"}
                "Production Type")
         (d/div {:className "content"}
                (:production-type mech))))

(q/defcomponent Manufacturer [mech]
  (d/div {:id "manufacturer" :className "entry"}
         (d/div {:className "label"}
                "Manufacturer")
         (d/div {:className "content"}
                (:manufacturer mech))))

(q/defcomponent Description [mech]
  (d/div {:id "description" :className "entry"}
         (d/div {:className "label"}
                "Description")
         (d/div {:className "content"}
                (:description mech))))

(q/defcomponent Height [mech]
  (d/div {:id "height" :className "entry"}
         (d/div {:className "label"}
                "Height")
         (d/div {:className "content"}
                (:height mech))))

(q/defcomponent Weight [mech]
  (d/div {:id "weight" :className "entry"}
         (d/div {:className "label"}
                "Weight")
         (d/div {:className "content"}
                (:weight mech))))

(q/defcomponent Power [mech]
  (d/div {:id "power" :className "entry"}
         (d/div {:className "label"}
                "Power Plant")
         (d/div {:className "content"}
                (:power mech))))

(q/defcomponent HorsePower [mech]
  (d/div {:id "horse-power" :className "entry"}
         (d/div {:className "label"}
                "Horsepower")
         (d/div {:className "content"}
                (:horse-power mech))))

(q/defcomponent PrimaryMovement [mech]
  (d/div {:id "primary-movement" :className "entry"}
         (d/div {:className "label"}
                "Primary Movement")
         (d/div {:className "content"}
                (:primary-movement mech))))

(q/defcomponent SecondaryMovement [mech]
  (d/div {:id "secondary-movement" :className "entry"}
         (d/div {:className "label"}
                "Secondary Movement")
         (d/div {:className "content"}
                (:secondary-movement mech))))

(q/defcomponent Weapon [weapon]
  (d/div {:id "weapon" :className "entry"}
         (d/div {:className "label"}
                (:label weapon))
         (when-not (= 0 (:ammo weapon))
           (d/div {:className "content"}
                  (:loadout weapon)))
         (d/div {:className "content"}
                (:label (:hardpoint weapon)))))

(q/defcomponent MechPayload [mech]
  (apply d/div {:id "sheet-container"}
         (map #(Weapon %) (:weapons mech))))

(q/defcomponent PayloadTitle [mech]
  (d/h1 {} "Weapon Payload"))

(q/defcomponent MechImage [mech]
  (d/div {:id "image"}
         (d/img {:src (:image mech)})))

(q/defcomponent MechSpecifications [mech]
  (d/div {:id "sheet-container"}
         (CodeName mech)
         (ProductionCode mech)
         (Manufacturer mech)
         (Description mech)
         (Height mech)
         (Weight mech)
         (Power mech)
         (HorsePower mech)
         (PrimaryMovement mech)
         (SecondaryMovement mech)))

(q/defcomponent Mech [mech]
  (d/div {}
         (MechImage mech)
         (MechSpecifications mech)
         (PayloadTitle mech)
         (MechPayload mech)))

(q/render (Mech mech)
          (.getElementById js/document "main"))
