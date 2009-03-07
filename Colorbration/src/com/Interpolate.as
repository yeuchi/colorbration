// ==================================================================
// Module:		Interpolate
//
// Description:	interpolation class, linear and cubic spline
//
// Reference:	Press, Teukolsky, Vetterling, Flannery, Numerical Recipies in C, 2nd Edition
//
// Input:		x coordinate (wavelength)
// Output:		interpolated y coordinate data 
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
package com {
	import flash.geom.Point;

	// ==================================================================================	
	//	Cubic Spline algorithm from Numerical recipies in C.
	//  Also found in Numerical Analysis text.
	// ==================================================================================
	public class Interpolate {
		
		protected var anchors:Array;									// knots, also known as anchors
		protected var nofk:int;											// number of knots
		protected var sMethod:String;									// method of interpolation
		protected var minX:Number;										// boundary points below
		protected var maxX:Number;
		protected var minY:Number;
		protected var maxY:Number;
		
		public static const INTERPOLATE_LINEAR:String = "LINEAR";
		public static const INTERPOLATE_CUBIC_SPLINE:String = "CUBIC_SPLINE";
		
		protected var aryB:Array;
		protected var aryC:Array;
		protected var aryD:Array;
		protected var aryH:Array;
		protected var arySIG:Array;
		protected var aryL:Array;
		protected var aryU:Array;
		protected var aryZ:Array;
		
		// ------------------------------------------------------------------------------
		public function Interpolate(anchors:Array=null,
									method:String=INTERPOLATE_LINEAR):void {
		// ------------------------------------------------------------------------------	
			//sMethod = method;
			sMethod = INTERPOLATE_LINEAR;
			init(anchors);
		}
		
		// ------------------------------------------------------------------------------
		// use to re-initialize or if object constructed without anchors
		public function init(anchors:Array):Boolean {
		// ------------------------------------------------------------------------------	
			if ( anchors.length < 2 )						// minimum of 2 knots
				return false;
				
			this.anchors = new Array();
			
			minY = Point(anchors[0]).y;
			maxY = Point(anchors[0]).y;
			
			for (var i:int=0; i<anchors.length; i++) {
				this.anchors.push(anchors[i]);
		
				// anchors must be sequential, sorted with respect to x axis.
				if ( i > 0 ) {
					if ( Point(anchors[i]).x < Point(anchors[i-1]).x ) {
						empty();
						return false;						
					}
				}
				minY = ( Point(anchors[i]).y < minY )? Point(anchors[i]).y : minY;
				maxY = ( Point(anchors[i]).y > maxY )? Point(anchors[i]).y : maxY;
			}	
			minX = Point(anchors[0]).x;
			maxX = Point(anchors[anchors.length-1]).x;
			
			if (( anchors.length > 2 ) && ( sMethod == INTERPOLATE_CUBIC_SPLINE ))
				formulateCubicSpline();
				
			return true;
		}
		
		// ------------------------------------------------------------------------------
		public function empty():void {
		// ------------------------------------------------------------------------------	
			this.anchors = null;
		}
		
		public function isEmpty():Boolean
		{
			if (this.count())
				return false;
			return true;
		}
				
		// ------------------------------------------------------------------------------
		public function count():int {
		// ------------------------------------------------------------------------------
			if ( anchors == null )
				return 0;
				
			return anchors.length;
		}
		
		// ------------------------------------------------------------------------------
		// calculate b, c, d for cublic spline equation
		// References: Numerical Analysis, Numerical Recipies in C
		protected function formulateCubicSpline():void 
		// ------------------------------------------------------------------------------
		{
			aryB = new Array();
			aryC = new Array();
			aryD = new Array();
			aryH = new Array();
			arySIG = new Array();
			aryL = new Array();
			aryU = new Array();
			aryZ = new Array();
		
			// Theorem 3.11		[A].[x] = [b]					[A] -> n x n Matrix
			//													[b] -> n x n Matrix
			//													[x] -> c[] 0..n
			//	STEP 1		eq. 4 (pg. 134)
			for (var aa:int = 0; aa < anchors.length-1; aa ++)
				aryH[aa] = Point(anchors[aa+1]).x - Point(anchors[aa]).x;		// [A], Hj = Xj+1 - Xj
		
			// STEP 2
			for (aa = 1; aa < anchors.length-1; aa ++)							// 0 -> n-1
				arySIG[aa] = (3.0/aryH[aa] * (Point(anchors[aa+1]).y - Point(anchors[aa]).y)) - 
								  (3.0/aryH[aa-1] * (Point(anchors[aa]).y - Point(anchors[aa-1]).y));
		
			// --- SOLVE FORE TRIDIAGONAL LINEAR EQUATION ----------
			
			// STEP 3
			aryL[0] = 1;
			aryU[0] = 0;
			aryZ[0] = 0;
		
			// STEP 4
			for (aa = 1; aa < anchors.length-1; aa ++)
			{
				aryL[aa] = (2.0 * (Point(anchors[aa+1]).x - Point(anchors[aa-1]).x)) - (aryH[aa-1] * aryU[aa-1]);
				aryU[aa] = aryH[aa] / aryL[aa];
				aryZ[aa] = (arySIG[aa] - (aryH[aa-1] * aryZ[aa-1])) / aryL[aa];
			}
		
			// STEP 5		TAIL BOUNDARY @ 0
			aryL[anchors.length-1] = 1;
			aryZ[anchors.length-1] = 0;
			aryC[anchors.length-1] = 0;
		
			// STEP 6
			for (aa = anchors.length-2; aa >= 0; aa --)
			{
				aryC[aa] = aryZ[aa] - (aryU[aa] * aryC[aa+1]);					// Theorem 3.11
				aryB[aa] = (Point(anchors[aa+1]).y - Point(anchors[aa]).y) / aryH[aa] 
					       - (aryH[aa] * (aryC[aa+1] + 2 * aryC[aa]) / 3);		// eq. 10
				aryD[aa] = (aryC[aa+1] - aryC[aa]) / (3 * aryH[aa]);			// eq. 11
			}
		}
		
		// ------------------------------------------------------------------------------
		public function addAnchor(pt:Point):Boolean {
		// ------------------------------------------------------------------------------	
			var index:int = bisection(pt.x);
			anchors.splice(index+1,0,pt);
			
			minX = ( pt.x < minX )? pt.x : minX;
			maxX = ( pt.x > maxX )? pt.x : maxX;
			minY = ( pt.y < minY )? pt.y : minY;
			maxY = ( pt.y > maxY )? pt.y : maxY;
			
			if (( anchors.length > 2 ) && ( sMethod == INTERPOLATE_CUBIC_SPLINE ))
				formulateCubicSpline();
				
			return true;	
		}
		
		// ------------------------------------------------------------------------------
		// remove a single entry in the LUT
		public function removeAnchor(x:Number):Boolean 
		// ------------------------------------------------------------------------------
		{
			if (anchors == null)
				return false;					// none to be matched
			
			for (var i:int=0; i<anchors.length; i++) {
				if ( x == Point(anchors[i]).x) { 
				 	anchors.splice(i, 1);	
				 	return true;				// found and removed
				}
				else if ( x < Point(anchors[i]).x)
				 	return false;				// did not find a match
			}
			return false;
		}
		
		// ------------------------------------------------------------------------------
		public function getAnchorByIndex(index:int):Point {
		// ------------------------------------------------------------------------------
			if (anchors == null)
				return null;
				
			if ((index < 0) || (index>=anchors.length))
				return null;
				
			return anchors[index];			
		}
		
		// ------------------------------------------------------------------------------
		public function getAnchor(x:Number):Point 
		// ------------------------------------------------------------------------------
		{
			if (anchors == null)
				return null;					// none to be matched
			
			for (var i:int=0; i<anchors.length; i++) {
				if ( x == Point(anchors[i]).x) 
				 	return anchors[i];			// mateched	
				 	
			 	else if ( x < Point(anchors[i]).x)
				 	return null;				// did not find a match
			}
			return null;
		}
		
		public function getAnchorIndex(x:Number):int {
			if (anchors == null)
				return -1;						// none to be matched
			
			for (var i:int=0; i<anchors.length; i++) {
				if ( x == Point(anchors[i]).x) 
				 	return i;					// mateched	
			}
			return -1;
		}
		
		// ------------------------------------------------------------------------------
		public function getY(x:Number):Number {
		// ------------------------------------------------------------------------------
			var index:int = bisection(x);
			
			if ( Point(anchors[index]).x == x )
				return Point(anchors[index]).y;
								 
			switch(sMethod) {
				case INTERPOLATE_CUBIC_SPLINE:
				return doCubicSpline(x, index);
				
				default:
				case INTERPOLATE_LINEAR:
				return doLinear(x, index);
			}
			return -1;
		}
		
		// ------------------------------------------------------------------------------
		public function getMinY():Number {
		// ------------------------------------------------------------------------------
			var min:Number = Point(anchors[0]).y
			for ( var i:int=1; i<anchors.length; i++) {
				if ( Point(anchors[i]).y < min)
					min = Point(anchors[i]).y;
			}
			return min;
		}
		
		// ------------------------------------------------------------------------------
		public function getMaxY():Number {
		// ------------------------------------------------------------------------------
			var max:Number = Point(anchors[0]).y
			for ( var i:int=1; i<anchors.length; i++) {
				if ( Point(anchors[i]).y > max)
					max = Point(anchors[i]).y;
			}
			return max;
		}
		
		// ------------------------------------------------------------------------------
		public function isAnchorX(x:Number):Boolean {
		// ------------------------------------------------------------------------------	
			if (anchors == null)
				return false;
			
			for (var i:int=0; i<anchors.length; i++) {
				if ( x == Point(anchors[i]).x) 
				 	return true;	
			}
			return false;
		}
		
		// ------------------------------------------------------------------------------
		public function isAnchor(pt:Point):Boolean {
		// ------------------------------------------------------------------------------
			if (anchors == null)
				return false;
				
			for (var i:int=0; i<anchors.length; i++) {
				if (( pt.x == Point(anchors[i]).x) &&
				 	( pt.y == Point(anchors[i]).y))
				 	return true;	
			}
			return false;
		}
		
		public function setAnchorByX(pt:Point):Boolean
		{
			if (anchors == null) return false;
			
			for (var i:int=0; i<anchors.length; i++) {
				if ( pt.x == Point(anchors[i]).x) {
					Point(anchors[i]).y = pt.y;
					return true;
				}
			}
			return false;
		}
		
		// ------------------------------------------------------------------------------
		public function setAnchorByIndex(index:int, pt:Point):Boolean
		// ------------------------------------------------------------------------------
		{
			if (anchors == null) return false;
			if (index > anchors.length-1) return false;
			if (index < 0) return false;
			
			Point(anchors[index]).x = pt.x;
			Point(anchors[index]).y = pt.y;
			return true;
		}
		
		// ------------------------------------------------------------------------------
		public function set method(choice:String):void {
		// ------------------------------------------------------------------------------	
			sMethod = choice;
		}
		
		// ------------------------------------------------------------------------------
		private function doLinear(	x:Number,	// input x position
									index:int)	// index in anchors that is relevant
									:Number {	// output y value
		// ------------------------------------------------------------------------------	
			return 	Number( Point(anchors[index+1]).y - Point(anchors[index]).y ) * 
					Number(x - Point(anchors[index]).x) /
					Number(Point(anchors[index+1]).x - Point(anchors[index]).x) +
					Number(Point(anchors[index]).y); 
		}
		
		// ------------------------------------------------------------------------------
		private function doCubicSpline(x:Number,
										index:int):Number {
		// ------------------------------------------------------------------------------	
			var Y:Number;
			
			Y = Point(anchors[index]).y									+ 
			 aryB[index] *	(x - Point(anchors[index]).x)				+
			 aryC[index] *	Math.pow((x - Point(anchors[index]).x), 2) 	+
			 aryD[index] * Math.pow((x - Point(anchors[index]).x), 3);
			return Y;
		}
		
		// ------------------------------------------------------------------------------
		// search array of anchors for nearest entry
		private function bisection(ab:Number):int {
		// ------------------------------------------------------------------------------
			var ju:int = anchors.length-1;							// upper limit
			var jl:int = 0;											// lower limit
			var jm:int;												// midpoint
		
			while (ju - jl > 1)							
			{
				jm = (ju + jl)/2;									// midpoint formula
		
				if (ab > Point(anchors[jm]).x)
					jl = jm;
				else
					ju = jm;
			}
			return jl;										
		}
		
	}
}