(ns mech.image)

(def base-mech-image {:width 480
                      :base 24})

(def mech-placeholder-image "//placekitten.com/g/480/640")

(defn torso [image]
  (assoc image
         :torso {:schema-x 4
                 :schema-y 3
                 :schema-width 6
                 :schema-height 7
                 :color "chartreuse"
                 :title "Torso"}))

(defn legs [image]
  (assoc image
         :legs {:schema-x 4
                :schema-y 8
                :schema-width 6
                :schema-height 7
                :color "goldenrod"
                :title "Legs"}))

(defn left-shoulder [image]
  (assoc image
         :left-shoulder {:schema-x 10
                         :schema-y 4
                         :schema-width 6
                         :schema-height 3
                         :color "honeydew"
                         :title "Left Shoulder"}))

(defn right-shoulder [image]
  (assoc image
         :right-shoulder {:schema-x 0
                         :schema-y 4
                         :schema-width 3
                         :schema-height 3
                         :color "powderblue"
                         :title "Right Shoulder"}))

(defn left-arm [image]
  (assoc image
         :left-arm {:schema-x 10
                    :schema-y 7
                    :schema-width 6
                    :schema-height 3
                    :color "indianred"
                    :title "Left Arm"}))

(defn right-arm [image]
  (assoc image
         :right-arm {:schema-x 0
                     :schema-y 7
                     :schema-width 3
                     :schema-height 3
                     :color "magenta"
                     :title "Right Arm"}))

(defn head [image]
  (assoc image
         :head {:schema-x 6
                :schema-y 0
                :schema-width 2
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
