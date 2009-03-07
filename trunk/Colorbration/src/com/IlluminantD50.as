// ==================================================================
// Module:		IlluminantD50
//
// Description:	a curve class for light source, D50
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
	public class IlluminantD50 extends SpectralData
	{
		public static const NAME:String = "D50";
		public function IlluminantD50()
		{
			super();
			name = NAME;
		}
		
		override public function onInitPercentData():void
		{						   
			percent = new Array(49.31, 56.51, 60.03, 57.82, 74.82, 
								87.25, 90.61, 91.37, 95.11, 91.96, 
								95.72, 96.61, 97.13, 102.1, 100.75, 
								102.32, 100, 97.74, 98.92, 93.5, 
								97.69, 99.27, 99.04, 95.72, 98.86, 
								95.67, 98.19, 103.0, 99.13, 87.38, 91.60);
		}
	}
}