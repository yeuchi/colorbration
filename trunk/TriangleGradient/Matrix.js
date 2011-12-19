// ===============================================================
// Module:		Matrix.js
// Description:	calculate the transform matrix for triangle gradient
// Reference:	Measuring color, Dr. Hunt
// Author:		C.T. Yeung
// ===============================================================

function inverse(mtx, 					// [out] 3x3 matrix to return
				 pSrcX, pSrcY, 			// [in] array of triangle reference color coordinates
				 clr0, clr1, clr2) {	// [in] triangle reference colors (corners)
			// matrix
			var dMtx = [pSrcX[0], pSrcY[0], 1,
				    	pSrcX[1], pSrcY[1], 1,
				    	pSrcX[2], pSrcY[2], 1];
			
			// inverse matrix
			var det = 	dMtx[0] * ( dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7] ) 
					  - dMtx[1] * ( dMtx[3] * dMtx[8] - dMtx[5] * dMtx[6] )
					  + dMtx[2] * ( dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6] );
			
			iMtx = [0,0,0,0,0,0,0,0,0];		
            iMtx[0] = ( dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7] ) / det;
            iMtx[3] = ( dMtx[6] * dMtx[5] - dMtx[3] * dMtx[8] ) / det;
            iMtx[6] = ( dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6] ) / det;
            iMtx[1] = ( dMtx[2] * dMtx[7] - dMtx[1] * dMtx[8] ) / det;
            iMtx[4] = ( dMtx[0] * dMtx[8] - dMtx[2] * dMtx[6] ) / det;
            iMtx[7] = ( dMtx[1] * dMtx[6] - dMtx[0] * dMtx[7] ) / det;
            iMtx[2] = ( dMtx[1] * dMtx[5] - dMtx[2] * dMtx[4] ) / det;
            iMtx[5] = ( dMtx[2] * dMtx[3] - dMtx[0] * dMtx[5] ) / det;
            iMtx[8] = ( dMtx[0] * dMtx[4] - dMtx[1] * dMtx[3] ) / det;
			
			// color matrix
			var r0 = parseInt(clr0.substring(0, 2), 16);
			var g0 = parseInt(clr0.substring(2, 4), 16);
			var b0 = parseInt(clr0.substring(4, 6), 16);
			
			var r1 = parseInt(clr1.substring(0, 2), 16);
			var g1 = parseInt(clr1.substring(2, 4), 16);
			var b1 = parseInt(clr1.substring(4, 6), 16);
			
			var r2 = parseInt(clr2.substring(0, 2), 16);
			var g2 = parseInt(clr2.substring(2, 4), 16);
			var b2 = parseInt(clr2.substring(4, 6), 16);
			
			// find matrix
			mtx[0] = r0 * iMtx[0] + g0 * iMtx[1] + b0 * iMtx[2];
			mtx[1] = r0 * iMtx[3] + g0 * iMtx[4] + b0 * iMtx[5];
			mtx[2] = r0 * iMtx[6] + g0 * iMtx[7] + b0 * iMtx[8];
			
			mtx[3] = r1 * iMtx[0] + g1 * iMtx[1] + b1 * iMtx[2];
			mtx[4] = r1 * iMtx[3] + g1 * iMtx[4] + b1 * iMtx[5];
			mtx[5] = r1 * iMtx[6] + g1 * iMtx[7] + b1 * iMtx[8];
			
			mtx[6] = r2 * iMtx[0] + g2 * iMtx[1] + b2 * iMtx[2];
			mtx[7] = r2 * iMtx[3] + g2 * iMtx[4] + b2 * iMtx[5];
			mtx[8] = r2 * iMtx[6] + g2 * iMtx[7] + b2 * iMtx[8];
		}
		