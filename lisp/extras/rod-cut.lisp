(defparameter maxm 'nil)
(defparameter price 'nil)

(defun initi(n)
	(setf maxm(make-array n :initial-element -1))
	(setf (aref maxm 0) 0)
	(setf price(make-array n :initial-element 0))
)

(defun dp(len)
	(cond 
       ((zerop len)0)
       ((not(= (aref maxm len) -1)) (aref maxm len) )
	   (t 
		  (loop for i from 1 to len 
				do(setf (aref maxm len) (max (+ (aref price i) (dp (- len i) ) ) (aref maxm len)) )
		  )
		  (aref maxm len)	
		)
	)
)

(defun read-()
	(with-open-file (in-file "E:\CS 427\\input_rod_cut.txt")
		(initi (1+ (read in-file)))
		(loop for i  from 1 to (1- (length maxm)) 
			do(setf (aref price i) (read in-file))
		)
	)		
)

(defun prin(len idx out)
	(cond ((zerop len)nil)
		  ((and (<= idx len)  (= (+ (aref price idx) (aref maxm (- len idx))) (aref maxm len))) (format out " ~a " idx) (prin (- len idx) idx out)) 
		  (t (prin len (1- idx ) out))	
    )
)

(defun save-len()
	(with-open-file (out-file "E:\CS 427\\output-rod-cut.txt"
						:direction :output
						:if-exists :supersede)
			(loop for i from 1 to (1- (length maxm))
				do(progn 
					(format out-file "length ~a maximum price is ~a , cuts are : " i (aref maxm i) )
					(prin i i out-file)
					(format out-file "~%" )
			     )
			)
	)
)

(defun rod-cut()
	(read-)
	(dp (1- (length maxm)))
	(save-len)
)

(rod-cut)

