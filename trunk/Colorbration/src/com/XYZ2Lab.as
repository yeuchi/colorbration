// ==================================================================
// Module:		XYZ2Lab
//
// Description:	calculation Tristimulus XYZ to and from L*a*b* coordinates
//
// Reference:	Billmeyer & Saltzman, Principle of Color Technology
//
// Author(s):	C.T. Yeung		cty
//
// Input:		TristimulusXZY object or L, a, b values for inverse
// Ouptut:		L, a, b or TristimulusXYZ for inverse
//
// History:
// 24Dec08		first started, functional						cty
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
	public class XYZ2Lab
	{
		public var cieXYZ:Spectral2XYZ;
		public var L:Number;
		public var a:Number;
		public var b:Number;
		
		public function XYZ2Lab()
		{
			
		}
		
		public function empty():void
		{
			if ( cieXYZ )
				cieXYZ.empty();
				
			cieXYZ = null;
			L = 0;
			a = 0;
			b = 0;
		}
		
		public function isEmpty():Boolean
		{
			if (!cieXYZ)
				return true;
			return false;
		}
		
		public function forward():Boolean
		{
			// CIE 1976 formulation
			var Y:Number = ( cieXYZ.Ys / cieXYZ.Yw );
			if ( Y < .008856 ) {
				L = 903.3 * Y;
				a = 500 * (cieXYZ.Xs/cieXYZ.Xw - Y);
				b = 200 * (Y - cieXYZ.Zs/cieXYZ.Zw);	
			}
			else {
				L = 116 * Math.pow(Y, 1/3) - 16;
				a = 500 * (Math.pow(cieXYZ.Xs/cieXYZ.Xw, 1/3) - Math.pow(Y, 1/3));
				b = 200 * (Math.pow(Y, 1/3) - Math.pow(cieXYZ.Zs/cieXYZ.Zw, 1/3));	
			}
			
			return true;
		}
		
		public function backward():Boolean
		{
			var Y:Number = Math.pow(L / 116 + 16, 3);
			if ( Y < .008856 )
			{
				
			}
			else
			{
				Y = L / 903.3;
			}
			return true;
		}
	}
}