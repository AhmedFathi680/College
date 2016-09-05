(defun load-file()
	(let ((str ""))
		(with-open-file (in-file "D:\College\\y4s2 CS427 - Programming Languages\\Lab\\Project\\huffman-input.txt")
		    (do
				((line (read-line in-file nil) (read-line in-file nil)))
				((null line))
				(setf str (concatenate 'string str line))
			)
		)
		str
	)
)

(defun save-codes(codes)
	(with-open-file (out-file "D:\College\\y4s2 CS427 - Programming Languages\\Lab\\Project\\huffman-output.txt"
						:direction 			:output
						:if-exists 			:supersede
						:if-does-not-exist 	:create
					)
		(princ (format nil "~{~a |=>~3t~a~%~}" codes) out-file)
	)
)


(defun calc-frequencies()
	(let ( (freq nil) (str (load-file))  (cur nil) )
		(loop for i from 0 to (1- (length str))
			do(progn	
				(setf cur (schar str i))
				(if (find cur freq :key #'car)
					(setf (cdr (find cur freq :key #'car)) (cons (1+ (cadr (find cur freq :key #'car))) nil) )
					(push (list cur 1) freq))
			)
		)
		freq
	)
)

(defun sort-freq(L)
	(sort L #'(lambda(x y) (if (< (cadr x) (cadr y) ) t nil )) )
)

(defun merge-two (L1 L2)
	(list (list  L1 L2) (+ (cadr L1) (cadr L2)))
)

(defun reduce-list(L)
		(setf L (sort-freq L))
		(setf L (cons (merge-two (car L) (cadr L)) (cddr L) )  )
		(if (> (length L) 1) (reduce-list L) (car L))
)

(defparameter codes nil)
(defun extract(L str)
	(if (listp (car L))
		(progn (extract (caar L) (concatenate 'string str "0")) (extract (cadar L) (concatenate 'string str "1")) )
		(push (list (car L) str) codes)
	)
	codes
)

(defun sort-alpha(L)
	(sort L #'(lambda(x y) (if (string-greaterp (car x) (car y)) t nil) ) )
)

(defun convert-to-plist(a-list)
	(let ((plist nil) (a-list  (sort-alpha a-list)))
		(mapcar #'(lambda(x)  (push (cadr x) plist) (push (car x) plist) ) a-list)
		plist
	)
)

(defun huffman()
	(save-codes (convert-to-plist (extract (reduce-list (calc-frequencies)) "")))
)

(defun hufman-tree())

(eval '(huffman))