package Views
{
	import flash.display.BitmapData;
	import flash.display.Shader;
	import flash.display.ShaderJob;
	import flash.filters.ShaderFilter;
	import flash.utils.ByteArray;
	
	public class RGB2HSV extends ColorConversion
	{
		public var h:Number;
		public var s:Number;
		public var v:Number;
		
		[Embed(source="pb/RGB2HSV.pbj", mimeType="application/octet-stream")]
		private var pbFilter:Class;
		private var shader:Shader;
		
		public function RGB2HSV(){
			shader = new Shader();
			shader.byteCode = new pbFilter() as ByteArray;
		}
		
		public function apply(bmd:BitmapData):BitmapData {
			shader.data.src.input = bmd;
			var job:ShaderJob = new ShaderJob(shader); 
			job.target = bmd; 
			job.start(true);
			return bmd;
		}
		
		// There might be something wrong here
		public function forward(color:uint):void {
			var min:Number;
			var max:Number;
			var delta:Number;
			var l:Number;
			split(color);
			
			// rgb to hsv
			min = r;
			if(min > g)  min = g;
			if(min > b)  min = b;
			
			max = r;
			if(max < g)  max = g;
			if(max < b)  max = b;
			v = max;
			
			delta = max - min;
			
			if(max!=0.0)
				s = delta/max;
			else {   // r = g = b = 0
				s = 0.0;
				h = -1.0;
			}
			
			if(r==max)       h = (g-b)/delta;
			else if (g==max) h = 2.0+(b-r)/delta;
			else if (b==max) h = 4.0+(r-g)/delta;
			
			h *= 60.0;
			if(h<0.0)
				h += 360.0;
		}
		
		public function forwardValue(color:uint):void {
			var max:Number;
			split(color);
			
			max = r;
			if(max < g)  max = g;
			if(max < b)  max = b;
			v = max;
		}
	}
}