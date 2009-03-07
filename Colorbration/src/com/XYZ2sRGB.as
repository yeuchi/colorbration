// ==================================================================
// Module:		sRGB
//
// Description:	sRGB to/from Tristimulus conversion
//				HP invented sRGB color space
//
// Reference:	http://en.wikipedia.org/wiki/SRGB#The_forward_transformation_.28CIE_xyY_or_CIE_XYZ_to_sRGB.29
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
	public class XYZ2sRGB extends Matrix3x3
	{
		public var X:Number;
		public var Y:Number;
		public var Z:Number;
		public var R:Number;
		public var G:Number;
		public var B:Number;
		
		public function XYZ2sRGB()
		{
			super();
			// xyz = m * sRGB
			dMtx[0] = 3.2410;
			dMtx[1] = -1.5374;
			dMtx[2] = -0.4986;
			dMtx[3] = -0.9692;
			dMtx[4] = 1.8760;
			dMtx[5] = 0.0416;
			dMtx[6] = 0.0556;
			dMtx[7] = -0.2040;
			dMtx[8] = 1.0570;
		}
		
		override public function empty():void
		{
			super.empty();
			X = Y = Z = R = G = B = 0;
		}
		
		override public function isEmpty():Boolean
		{
			if (!X&&!Y&&!Z&&!R&&!G&&!B)
				return true;
			return false;
		}
		
		public function forward():Boolean
		{
			if (!multiply(X/100.0, Y/100.0, Z/100.0))
				return false;
			
			var linear:Array = new Array(0,0,0);
			linear[0] = out1;
			linear[1] = out2;
			linear[2] = out3;
			
			for ( var i:int = 0; i<3; i++) {
				linear[i] = (linear[i]<=0.0031308)?12.92*linear[i] :1.055*Math.pow(linear[i], (1.0/2.4))-.055;
			}
			R = linear[0]*255.0;
			G = linear[1]*255.0;
			B = linear[2]*255.0;
			
			R = requant(R);
			G = requant(G);
			B = requant(B);
			return true;
		}
		
		protected function requant(n:Number):Number
		{
			if (n<0)		return 0;
			else if (n>255) return 255;
			else return n;
		}
	}
}