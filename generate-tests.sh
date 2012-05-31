#!/bin/bash

testNumber=$1
scale=$2
# Compile the source 
javac GraphGenerator.java
rm -rf tests
# make the dir buffer
mkdir tests

if [ $# -ne 2 ]
then
    echo "$0: arg1: number of tests && arg2: scale" >&2
    exit 1
fi

echo "Generating tests from 0 to $testNumber"
i=1
while [ $i -le $testNumber ]
do
    timestamp=`expr $i \* $scale`
    if [ $i -lt 10 ]
    then
	echo "1 $timestamp" | java GraphGenerator > "tests/t0$i.in"
    else
	echo "1 $timestamp" | java GraphGenerator > "tests/t$i.in"
    fi
    i=`expr $i + 1`
    echo "generated test $i"
done
rm GraphGenerator.class
echo "done it :)"
