# JSON Scala parsing performance tests — Performance tests for Scala JSON parsing options #

## Build & run ##

```sh
$ cd JSON-Scala-parsing-performance-tests
$ ./sbt
> run
```

There are several command-line arguments for

com.fourtysevendeg.jsonScalaPerftest.Main

-iterations n is the number of iterations to run; the default value is 200, and the result is the mean value
-warmup n is the number of iterations for warm-up, i.e., executed prior to taking measurements; the default
value is 25
-exclude a,b,c is a list of parsers to exclude from the test
- operation op where op is p (parse), up (unparse), m (map), um (unmap, NYI); the default is p
- report r where r is c (console) or b (bar chart); default is c
-report title is the chart title

## Contact ##

- John Nestor
- <a href="mailto:nestor@persist.com">nestor@persist.com</a>

- Dragos Manolescu
- <a href="mailto:coder@micro-workflow.com">coder@micro-workflow.com</a>
