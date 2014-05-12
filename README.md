# Extract map run data from Hadoop job HTML log file

Note: Java 8 project.

```
$ gradle shadowJar
$ java -jar build/distributions/extract-hadoop-map-data-unspecified-shadow.jar <all_MAP_task_list_for_0059.html>
```

To get an idea of the run times here is the data from running the tool on a HTML table with 20.000 rows on a MacBook Air

```
dalum-air:Nanite eksperiment pmd$ time java -jar extract-hadoop-map-data-unspecified-shadow.jar Hadoop\ Job\ 0073/all\ MAP\ task\ list\ for\ 0073.html > Hadoop\ Job\ 0073/map-run-times.csv
Will parse Hadoop Job 0073/all MAP task list for 0073.html to stdout

real0m6.376s
Users0m7.313s
sys0m0.690s

dalum-air:Nanite eksperiment pmd$ wc Hadoop\ Job\ 0073/map-run-times.csv 
         19830   79320 1541378 Hadoop Job 0073/map-run-times.csv
```
