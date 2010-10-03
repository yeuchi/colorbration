// ==================================================================
// Module:		WeightedTriangle.as
//
// Description:	This is my interpretation of what Illuminati's pixelbender 
//				filter is attempting to achieve.  Or at least it is what
//				I am trying to achieve using simple algebra and geometry.
//				A much more efficient method would be to use 3x3 matrx.
//
// Reference:	http://www.adobe.us/cfusion/exchange/index.cfm?event=extensionDetail&loc=en_us&extid=1937522
//
// Author(s):	C.T. Yeung		cty
//
// History:
// 15Sep10		first started									cty
// 01Oct10		Working great									cty
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
package GradientMethods
{
	import GradientMethods.Triangle;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.GraphicsPathCommand;
	import flash.display.GraphicsPathWinding;
	import flash.display.Shader;
	import flash.display.ShaderJob;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.ByteArray;
	import flash.utils.getTimer;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	public class WeightedTriangle extends Triangle
	{
		[Embed("assets/TriangleGradient.pbj", mimeType="application/octet-stream")] protected var weightedFilter:Class;
		
		public function WeightedTriangle(){
			super();
		}
		
		override protected function getShader():Shader {
			return new Shader(new weightedFilter());
		}
		
		override public function drawTriangle():uint {
			// build path for draw
			var st:uint = getTimer();
			cmds = new Vector.<int>();
			data = new Vector.<Number>();
			cmds.push(GraphicsPathCommand.MOVE_TO);
			data.push(pa.x);
			data.push(pa.y);
			
			cmds.push(GraphicsPathCommand.LINE_TO);
			data.push(pb.x);
			data.push(pb.y);
			
			cmds.push(GraphicsPathCommand.LINE_TO);
			data.push(pc.x);
			data.push(pc.y);
			
			// do the pixelbender thing !
			shader = getShader();
			shader.data.ptA.value  = [pa.x, pa.y];
			shader.data.ptB.value  = [pb.x, pb.y];
			shader.data.ptC.value  = [pc.x, pc.y];
			
			var lineBC:Line = new Line(pb, pc);
			shader.data.disA.value = [lineBC.distance(pa)];
			var lineAC:Line = new Line(pa, pc);
			shader.data.disB.value = [lineAC.distance(pb)];
			var lineAB:Line = new Line(pa, pb);
			shader.data.disC.value = [lineAB.distance(pc)];
			
			var a:Number = 1;
			var r:Number = Number((ca&0xFF0000)>>16)/255.0;
			var g:Number = Number((ca&0xFF00)>>8)/255.0;
			var b:Number = Number(ca&0xFF)/255.0;
			
			shader.data.clrA.value  = [a,r,g,b];
			
			a = 1;
			r = Number((cb&0xFF0000)>>16)/255.0;
			g = Number((cb&0xFF00)>>8)/255.0;
			b = Number(cb&0xFF)/255.0;
			
			shader.data.clrB.value  = [a,r,g,b];
			
			a = 1;
			r = Number((cc&0xFF0000)>>16)/255.0;
			g = Number((cc&0xFF00)>>8)/255.0;
			b = Number(cc&0xFF)/255.0;
			
			shader.data.clrC.value  = [a,r,g,b];
			
			// draw !
			graphics.beginShaderFill(shader);
			graphics.drawPath(cmds,data,GraphicsPathWinding.NON_ZERO);
			var end:uint = getTimer();
			return end - st;
		}
	}
}