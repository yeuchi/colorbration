// ==================================================================
// Module:		SpectralData
//
// Description:	a curve class for visble spectral data (400-700nm)
//				of a sample
//
// Input:		x coordinate (wavelength)
// Output:		interpolated y coordinate data 
//				(percent reflectance if used for source or sample)
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
	import flash.geom.Point;
	
	public class SpectralData extends Interpolate
	{
		public static const NAME:String = "Default 50% N";
		public var name:String = NAME;
		protected var wavelength:Array;
		protected var percent:Array;
		
		public function SpectralData()
		{
			onInitWavelengthData();
			onInitPercentData();
			var data:Array = onInitCurve();
			super(data, Interpolate.INTERPOLATE_CUBIC_SPLINE);
		}
		
		public function onInitWavelengthData():void
		{
			wavelength = new Array(400, 410, 420, 430, 440,
								   450, 460, 470, 480, 490,
								   500, 510, 520, 530, 540,
								   550, 560, 570, 580, 590,
								   600, 610, 620, 630, 640,
								   650, 660, 670, 680, 690,
								   700);
		}
		
		public function onInitPercentData():void
		{	
			percent = new Array();
			
			for ( var i:int=0; i<31; i++ )
				percent.push(50);					   
		}
		
		protected function onInitCurve():Array
		{
			var data:Array = new Array();
			for ( var i:int=0; i<wavelength.length; i++)
			{
				var pt:Point = new Point(wavelength[i], percent[i]);
				data.push(pt);
			}
			return data;
		}
	}
}