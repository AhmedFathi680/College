(defparameter str1 nil)
(defparameter str2 nil)
(defparameter max-length nil)

(defun initi(n m)
	(setf max-length (make-array (list n m) :initial-element -1) )
)

(defun lcs(i  j)
	(cond 
		( (or (= i  (length str1) ) (= j (length str2) ) ) 0)
		( (not (= (aref max-length i j) -1) ) (aref max-length i j) )	
		( (eq (schar str1 i)  (schar str2 j) ) (setf (aref max-length i j) (1+ (lcs (1+ i) (1+ j)) )   )  )
		( t (setf (aref max-length i j) (max (lcs (1+ i) j)  (lcs i (1+ j)))))
	)
)

(defun print-lcs(out)
	(let ((k 0))
		(loop for i from 0 to (1- (length str1) )
			do(loop for j from k to (1- (length str2))
				do(if(=(1+ (lcs (1+ i) (1+ j))) (lcs i j))
                     (progn (setf k (1+ j)) (format out "~a" (schar str2 j))  (return))
				 )
			)
		)
	)
)

(defun read-()
	(with-open-file (in-file "E:\CS 427\\input_lcs.txt")
		(setf str1 (read-line in-file))
		(setf str2 (read-line in-file))
	)		
)

(defun save-lcs()
	(with-open-file (out-file "E:\CS 427\\output-lcs.txt"
						:direction :output
						:if-exists :supersede)
					(print-lcs out-file)
					(format out-file "~%" )
	)
)

(defun Longest-Common-Subsequence()
	(read-)
	(initi (length str1) (length str2))
	(save-lcs)
)

(Longest-Common-Subsequence)
