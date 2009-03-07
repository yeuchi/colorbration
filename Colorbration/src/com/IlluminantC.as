// ==================================================================
// Module:		IlluminantC
//
// Description:	a curve class for light source, C
//
// Reference:	Billmeyer & Saltzman, Principle of Color Technology
//
// Input:		x coordinate (wavelength)
// Output:		interpolated y coordinate data 
//				(percent reflectance, source)
//
// Author(s):	C.T. Yeung		cty
//
// History:
// 24Dec08		first started									cty
//
// Copyright (c) 2009 C.T.Yeung
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:

// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
// CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
// TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
// SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// ==================================================================
package com
{
	public class IlluminantC extends SpectralData
	{
		public static const NAME:String = "C";
		public function IlluminantC()
		{
			super()
			name = NAME;
		}
		
		override public function onInitPercentData():void
		{						   
			percent = new Array(63.30, 80.60, 98.10, 112.40, 121.50, 
								124, 123.1, 123.8, 123.9, 120.7, 
								112.1, 102.3, 96.9, 98, 102.1, 
								105.2, 105.3, 102.3, 97.8, 93.2, 
								89.7, 88.4, 88.1, 88, 87.8, 
								88.2, 87.9, 86.3, 84, 80.2, 76.3);
		}
	}
}