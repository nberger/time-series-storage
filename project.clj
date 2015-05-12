(defproject org.clojars.nberger/time-series-storage "0.1.7.2"
  :description "Time Series Storage Library"
  :url "https://github.com/guilespi/time-series-storage"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [clj-time "0.8.0"]
                 [sqlingvo "0.7.10"]])
