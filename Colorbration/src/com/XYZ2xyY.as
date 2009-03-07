// ==================================================================
// Module:		XYZ2xyY
// 
// Description:	CIE Tristimulus XYZ conversion to/from Chromaticity xyY 
//
// Reference:	Billmeyer & Saltzman, Principle of Color Technology
//				2nd edition.
//
// Input/Output:		
//				Tristimulus sample & source for Chromaticity xyY or 
//				reverse for the opposite
//
// Author(s):	C.T. Yeung			cty
//
// History:
// 09Jan09		functional forward								cty
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
	public class XYZ2xyY
	{
		// Chromaticity xy
		public var xc:Number;
		public var yc:Number;
		
		// Tristimulus XYZ
		public var X:Number;
		public var Y:Number;
		public var Z:Number;
		
		public function XYZ2xyY()
		{
		}

		public function empty():void
		{
			xc = 0;
			yc = 0;
			X = 0;
			Y = 0;
			Z = 0;
		}
		
		public function isEmpty():Boolean
		{
			if (!xc && !yc && !X && !Y && !Z)
				return true;
			return false;
		}
		
		public function forward():Boolean
		{
			var denom:Number = (X + Y + Z);
			xc = X / denom;
			yc = Y / denom;
			return true;
		}
		
		public function backward():Boolean
		{
			return true;
		}
	}
}