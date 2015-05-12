(ns time-series-storage.query-test
  (:require [clj-time.coerce :as tcoerce]
            [clj-time.core :as t])
  (:use clojure.test
        time-series-storage.query))

(deftest fill-range-collapse-average-test
  (testing "1 data point trivial timeseries"
    (let [start #inst "2015-03-21T09:00:00"
          data-points [{:timestamp (tcoerce/to-string start) :total 20 :counter 2 :key "some-key"}]]
      (is (= [{:key "some-key" :timestamp (tcoerce/to-string start) :total 20 :counter 2}]
             (fill-range start
                         #inst "2015-03-21T09:40:00"
                         :hour
                         (collapse data-points :hour))))))

  (testing "many data points timeseries"
    (let [start (tcoerce/from-date #inst "2015-03-21T09:00:00")
          data-points [{:timestamp (tcoerce/to-string start) :total 20 :counter 2 :key "some-key"}
                       {:timestamp (tcoerce/to-string (t/plus- start (t/minutes 40))) :total 30 :counter 1 :key "some-key"}
                       {:timestamp (tcoerce/to-string (t/plus- start (t/minutes 80))) :total 34 :counter 8 :key "some-key"}
                       {:timestamp (tcoerce/to-string (t/plus- start (t/minutes 130))) :total 42 :counter 4 :key "some-key"}
                       ]
          collapsed (collapse data-points :hour)]
      (is (= (->> [{:timestamp (tcoerce/to-string start) :total 50 :counter 3}
                   {:timestamp (tcoerce/to-string (t/plus- start (t/hours 1))) :total 34 :counter 8}
                   {:timestamp (tcoerce/to-string (t/plus- start (t/hours 2))) :total 42 :counter 4}]
                  (map (partial merge {:key "some-key"})))
             (fill-range (tcoerce/to-date start)
                         #inst "2015-03-21T11:40:00"
                         :hour
                         collapsed))))))
