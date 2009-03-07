// ==================================================================
// Module:		Matrix3x3
//
// Description:	3x3 matrix multiplication and inversion
//
// Reference:	Press, Teukolsky, Vetterling, Flannery, Numerical Recipies in C, 2nd Edition
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
	public class Matrix3x3
	{
		public var dMtx:Array;
		protected var iMtx:Array;
		public var out1:Number;
		public var out2:Number;
		public var out3:Number;
		
		public function Matrix3x3()
		{
			empty();
		}
		
		public function empty():void
		{
			dMtx = new Array(0,0,0,
							 0,0,0,
							 0,0,0);
			iMtx = new Array(0,0,0,
							 0,0,0,
							 0,0,0);
		}
		
		public function isEmpty():Boolean
		{
			if ((dMtx.length != 9) ||
			    (dMtx[0] == dMtx[1] == dMtx[2] == 
			     dMtx[3] == dMtx[4] == dMtx[5] ==
			     dMtx[6] == dMtx[7] == dMtx[8] == 0 ))
				return true;
			return false;
		}
		
		// ----------------------------------------------------------
		public function multiply(in1:Number, 
								 in2:Number,
								 in3:Number,
								 mtx:Array=null)
								 :Boolean
		// ----------------------------------------------------------
		{
			if (isEmpty())
				return false;
				
			out1 = in1 * dMtx[0] + in2 * dMtx[1] + in3 * dMtx[2];
			out2 = in1 * dMtx[3] + in2 * dMtx[4] + in3 * dMtx[5];
			out3 = in1 * dMtx[6] + in2 * dMtx[7] + in3 * dMtx[8];
			return true;
		}
		
		// ----------------------------------------------------------
		public function invMultiply( in1:Number, 
									 in2:Number,
									 in3:Number)
									 :Boolean
		// ----------------------------------------------------------
		{
			if (isEmpty())
				return false;
			
			inverse();
			return multiply(in1, in2, in3);
		}
		
		public function inverse():Boolean
		{
			var det:Number;
			det = dMtx[0] * ( dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7] ) 
			      - dMtx[1] * ( dMtx[3] * dMtx[8] - dMtx[5] * dMtx[6] )
			      + dMtx[2] * ( dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6] );
		
			iMtx[0] = ( dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7] ) / det;
			iMtx[3] = ( dMtx[6] * dMtx[5] - dMtx[3] * dMtx[8] ) / det;
			iMtx[6] = ( dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6] ) / det;
			iMtx[1] = ( dMtx[2] * dMtx[7] - dMtx[1] * dMtx[8] ) / det;
			iMtx[4] = ( dMtx[0] * dMtx[8] - dMtx[2] * dMtx[6] ) / det;
			iMtx[7] = ( dMtx[1] * dMtx[6] - dMtx[0] * dMtx[7] ) / det;
			iMtx[2] = ( dMtx[1] * dMtx[5] - dMtx[2] * dMtx[4] ) / det;
			iMtx[5] = ( dMtx[2] * dMtx[3] - dMtx[0] * dMtx[5] ) / det;
			iMtx[8] = ( dMtx[0] * dMtx[4] - dMtx[1] * dMtx[3] ) / det;
			
			return true;
		}

	}
}