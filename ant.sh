#!/bin/bash
ant|grep "cannot be cast"|sort -u|sed 's/.*ClassCastException: class //'| sed 's/(.*//'|sort -u
