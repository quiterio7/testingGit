#!/bin/bash

# @Author - Alexandre Quiterio

#---- variables --------#
tests=$1
test_dir="tests"
solution="MailMan"
aux_file="buffer.out"

# Main function
# test target is executed several times
execute()
{
    for i in $(seq 1 1 $tests)
    do
    echo "--------------------" 1>&2
	echo "test $i: " 1>&2
	echo "testing $i..."
	    for j in $(seq 1 1 9)
	    do
	        if [ $i -lt 10 ]
	        then
          	    cat "$test_dir/t0$i.in" | java "$solution" > $aux_file
	        else
           	    cat "$test_dir/t$i.in" | java "$solution" > $aux_file
	        fi
	    done
    done
}

#main script
if [ $# -lt 1 ]
then
    echo "usage: number of tests"
    exit 1
else
    javac $solution.java
    echo "starting tests ..."
    execute 2> "values".out
    rm $aux_file *.class
    echo "done it :)"
fi
