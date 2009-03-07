// ==================================================================
// Module:		Illuminants
//
// Description:	container of all the illuminants
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
	public class Illuminants
	{
		[Bindable]public var nameList:Array;
		[Bindable]public var objList:Array;
		
		public function Illuminants()
		{
			nameList = new Array();
			objList = new Array();
			onInit();
		}

		protected function onInit():void
		{
			var D65:IlluminantD65 = new IlluminantD65();
			objList.push(D65);
			nameList.push(D65.name);
			
			var D50:IlluminantD50 = new IlluminantD50();	
			objList.push(D50);
			nameList.push(D50.name);
			
			var A:IlluminantA = new IlluminantA();	
			objList.push(A);
			nameList.push(A.name);
			
			var B:IlluminantB = new IlluminantB();	
			objList.push(B);
			nameList.push(B.name);
			
			var C:IlluminantC = new IlluminantC();	
			objList.push(C);
			nameList.push(C.name);
		}
	}
}