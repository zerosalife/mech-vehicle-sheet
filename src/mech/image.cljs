(ns mech.image)

(def base-mech-image {:width 480
                      :base 24})

(def mech-placeholder-image "//placekitten.com/g/480/640")

(defn torso [image]
  (assoc image
         :torso {:schema-x 4
                 :schema-y 3
                 :schema-width 7
                 :schema-height 6
                 :color "chartreuse"
                 :title "Torso"}))

(defn legs [image]
  (assoc image
         :legs {:schema-x 2
                :schema-y 9
                :schema-width 11
                :schema-height 16
                :color "goldenrod"
                :title "Legs"}))

(defn left-shoulder [image]
  (assoc image
         :left-shoulder {:schema-x 11
                         :schema-y 2
                         :schema-width 3
                         :schema-height 3
                         :color "honeydew"
                         :title "Left Shoulder"}))

(defn right-shoulder [image]
  (assoc image
         :right-shoulder {:schema-x 1
                         :schema-y 2
                         :schema-width 3
                         :schema-height 3
                         :color "powderblue"
                         :title "Right Shoulder"}))

(defn left-arm [image]
  (assoc image
         :left-arm {:schema-x 12
                    :schema-y 5
                    :schema-width 3
                    :schema-height 8
                    :color "indianred"
                    :title "Left Arm"}))

(defn right-arm [image]
  (assoc image
         :right-arm {:schema-x 0
                     :schema-y 5
                     :schema-width 3
                     :schema-height 8
                     :color "magenta"
                     :title "Right Arm"}))

(defn head [image]
  (assoc image
         :head {:schema-x 5
                :schema-y 0
                :schema-width 5
                :schema-height 4
                :color "cyan"
                :title "Head"}))


(defn mech-image []
  (-> base-mech-image
      torso
      legs
      left-shoulder
      right-shoulder
      left-arm
      right-arm
      head))
