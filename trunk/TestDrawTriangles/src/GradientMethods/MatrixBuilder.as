// ==================================================================
// Module:		MatrixBuilder.as
//
// Description:	For use in 3D triangle gradient but can be use in
//				any 3D transform, especially in color transform.
//
// Author(s):	C.T. Yeung
//
// History:
//	originally done in the early 90's for color transform but
//  will use it here for triangle gradient in Actionscript.
// ==================================================================
package GradientMethods
{
	import flash.geom.Matrix3D;

	public class MatrixBuilder
	{
		public function MatrixBuilder()
		{
		}
		
		public static create (mRGB:Array,		// [in] RGB for points 1,2,3
							  mXYZ:Array)		// [in] XYZ for points 1,2,3
							 :Array {			// [out] matrix to interpolate RGB for every XYZ
			var mPts:Matrix3D = new Matrix3D();
			
		}
	}
}