#!/bin/sh
# qqwing - Sudoku solver and generator
# Copyright (C) 2014 Stephen Ostermiller
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License along
# with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
set -e

if [ -e target/qqwing ]
then
	newer=`find src/cpp/ target/automake/Makefile -type f -newer target/qqwing`
	if [ "z$newer" = "z" ]
	then
		exit 0
	fi
fi

echo "Building target/qqwing"
rm -rf target/automake/qqwing target/qqwing target/.libs target/automake/*.o target/automake/*.lo target/automake/*.la target/automake/.libs/ target/automake/autom4te.cache/*
cp src/cpp/*.cpp target/automake
cp src/cpp/*.hpp target/automake
cd target/automake
make
cd ../..
cp target/automake/qqwing target/qqwing
ls target/qqwing
