// ==================================================================
// Module:		IlluminantA
//
// Description:	a curve class for light source, A
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
	public class IlluminantA extends SpectralData
	{
		public static const NAME:String = "A";
		public function IlluminantA()
		{
			super();
			name = NAME;
		}
		
		override public function onInitPercentData():void
		{						   
			percent = new Array(14.71, 17.68, 20.99, 24.67, 28.70, 
								33.09, 37.81, 42.87, 48.24, 53.91, 
								59.86, 66.06, 72.50, 79.13, 85.95, 
								92.91, 100.00, 107.18, 114.44, 121.73, 
								129.04, 136.35, 143.62, 150.84, 157.98, 
								165.03, 171.96, 178.77, 185.43, 191.93, 198.26);
		}
	}
}