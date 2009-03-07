// ==================================================================
// Module:		Spectral2XYZ
//
// Description:	CIE Tristimulus XYZ calculations from reflectance
//				at 10nm resolution.
//
// Reference:	Billmeyer & Saltzman, Principle of Color Technology
//				2nd edition.
//
// Input:		data points representing a spectral curve (400-700nm)
// Output:		Tristimulus XYZ value
//
// Author(s):	C.T. Yeung			cty
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
	import flash.geom.Point;
	
	public class Spectral2XYZ extends StandardObserver
	{
		public var observer:StandardObserver;
		protected var _sample:SpectralData;
		protected var _source:SpectralData;
				
		public var Kw:Number;								// factor to bring Y to 100%
		public var Xw:Number;
		public var Yw:Number;
		public var Zw:Number;
		
		public var Xs:Number;
		public var Ys:Number;
		public var Zs:Number;
		
		public function Spectral2XYZ()
		{
			super();
		}
		
		public function empty():void
		{
			if ( _sample ) {
				_sample.empty();
				_sample = null;
			}
			if ( _source ) {
				_source.empty();
				_source = null;
			}
		}
		
		public function isEmpty():Boolean
		{
			if (_sample || _source)
				return false;
			return true;
		}

/////////////////////////////////////////////////////////////////////
// methods
		
		// ----------------------------------------------------------
		// Produce Tristimulus values:
		// take the integral of sample and standard observer curves.
		// ----------------------------------------------------------
		public function set sample(sam:SpectralData):void
		{
			_sample = sam;
			
			if (!_source) 
				return;
				
			Xs = Ys = Zs = 0;
			for (var i:int=0; i<stdObserverX.count(); i ++) {
				var anchor:Point = stdObserverX.getAnchorByIndex(i);
				Xs += Point(stdObserverX.getAnchorByIndex(i)).y * _sample.getY(anchor.x)/100.0 * _source.getY(anchor.x)/100.0;
				Ys += Point(stdObserverY.getAnchorByIndex(i)).y * _sample.getY(anchor.x)/100.0 * _source.getY(anchor.x)/100.0;
				Zs += Point(stdObserverZ.getAnchorByIndex(i)).y * _sample.getY(anchor.x)/100.0 * _source.getY(anchor.x)/100.0;
			}
			Xs *= Kw;
			Ys *= Kw;
			Zs *= Kw;
		}
		
		public function set source(src:SpectralData):void
		{
			_source = src;
			Xw = Yw = Zw = 0;
			for (var i:int=0; i<stdObserverX.count(); i ++) {
				var anchor:Point = stdObserverX.getAnchorByIndex(i);
				Xw += Point(stdObserverX.getAnchorByIndex(i)).y * _source.getY(anchor.x)/100.0;
				Yw += Point(stdObserverY.getAnchorByIndex(i)).y * _source.getY(anchor.x)/100.0;
				Zw += Point(stdObserverZ.getAnchorByIndex(i)).y * _source.getY(anchor.x)/100.0;
			}
			Kw = 100.0 / Yw;
			
			Xw *= Kw;
			Yw *= Kw;
			Zw *= Kw;
			
			if ( _sample)
				sample = _sample;
		}
		
		public function get sample():SpectralData
		{
			return _sample;
		}
		
		public function get source():SpectralData
		{
			return _source;
		}
	}
}