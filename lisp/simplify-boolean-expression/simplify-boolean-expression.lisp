(defun remove-inproper-spaces-dots(str)
	(setf str (concatenate 'string "(" str ")"))
	(let ((res (string "")))
		(loop for i from 0 to (1- (length str))
			do(if (not (or (eq (schar str i) #\.) (eq (schar str i) #\space) ))
				(setf res (concatenate 'string res (string(schar str i))) )
			)
		)
		(put-dots 0 res)
	)
)

(defun need-dot-p(char1 char2)
	(cond 
		((and (or (eq char1 #\0) (eq char1 #\1) (alpha-char-p char1)) (or (eq  char2 #\() (eq char2 #\0) (eq char2 #\1) (alpha-char-p char2)) )  t)
		((and (or (eq  char1 #\)) (eq  char1 #\'))   (or (eq char2 #\() (eq char2 #\0) (eq char2 #\1) (alpha-char-p char2)) )  t)
		(t nil)
	)
)

(defun put-dots(idx str)
	(if (= idx (length str)) ""
		(if 
			(and (< 0 idx) (need-dot-p (schar str (1- idx)) (schar str idx)))
			(concatenate 'string "." (string (schar str idx))  (put-dots (1+ idx) str) )
			(concatenate 'string (string (schar str idx))  (put-dots (1+ idx) str) )
		)
	)
)

(defun oper-p (ch) (if (or (eq ch #\.) (eq ch #\+) (eq ch #\') (eq ch #\() (eq ch #\)) ) t nil))
(defun value-p (ch)  (if (or (eq ch #\0) (eq ch #\1) (alpha-char-p ch)  ) t nil) )
(defun string-expr->infix-list(str)
	(setf str (remove-inproper-spaces-dots str))
	(let ((value nil) (ops nil) (ch nil) (op_ref (list #\' 'not #\. 'and #\+ 'or #\( #\( #\) #\) ) ) )
		(loop for i from 0 to (1- (length str))
			do(progn
				(setf ch (schar str i))
				(when (value-p ch)
					(cond
						((eq ch #\0)  (push nil value))
						((eq ch #\1) (push t value))
						(t (push (intern (string-upcase ch)) value))
					)
				)
				(when (oper-p ch)
					(setf ch (getf op_ref ch))
					(cond
						((eq ch #\))
							(loop
								(when (eq (car ops) #\() (pop ops) (return) )
								(push (list (first ops) (second value) (first value)) (cddr value))
								(pop value) (pop value) (pop ops)
							)
						)
						((eq ch #\() (push ch ops))
						((eq ch 'not) (push (list 'not (first value)) (cdr value) ) (pop value) )
						((eq ch 'or)  (loop  (unless (eq (first ops) 'and) (return))   
											(push (list (first ops) (second value) (first value)) (cddr value))
											(pop value) (pop value) (pop ops)
									)
									(push ch ops)
						)
						((eq ch 'and) (push ch ops))
					)
				)
			)
		)
		(car value)
	)
)

(defun absorb (L)
	(cond 
		((eq (car L) 'and) (simplify-and (cons 'and (absorb-and (sort (cdr L) #'(lambda(a b) (if (listp a) nil t)) ) ))))
		((eq (car L) 'or)  (simplify-or (cons 'or (absorb-or (sort (cdr L) #'(lambda(a b) (if (listp a) nil t)) ) ))))
		(t L)
	)
)

(defun absorb-complement-and (cur L)
	(mapcar #'(lambda (x)  (if (and (listp x) (eq (car x) 'not) (member cur x))  nil x )) L)
)

(defun absorb-complement-and-or(cur L)
	;==> x.(x'+y)
	;=> x.x' + x.y
	;=> 0 + x.y
	;=> x.y

	; (AND X (OR (NOT X) Y)) =>  (OR X Y)
)


(defun absorb-and (L)
	(if L
		(cons (car L) (absorb-and (absorb-complement-and (car L) (remove-if-not #'(lambda (x)  (if (and (listp x) (eq (car x) 'or))  (not (member (car L) x))  t)  ) (cdr L) )  )) )
		nil
	)
)

(defun absorb-complement-or-and(cur L)
	;==> x + x'y 
	;=> (x+x').(x+y)
	;=> 1.(x+y)
	;=> x+y
	                                                                                                                        
	;(OR X (AND (NOT X) Y)) => (OR X Y)
)

(defun absorb-complement-or (cur L)
	(mapcar #'(lambda (x)  (if (and (listp x) (eq (car x) 'not) (member cur x))  t x )) L)
)

(defun absorb-or (L)
	(if L
		(cons (car L) (absorb-or (absorb-complement-or (car L) (remove-if-not #'(lambda (x)  (if (and (listp x) (eq (car x) 'and))  (not (member (car L) x))  t)  ) (cdr L) ) ) ) )
		nil
	)
)

(defun simplify-and (L)
	(let ((L2 nil))
		(mapcar #'(lambda(x) (if (and (listp x) (eq (car x) 'and)) (setf L2 (append L2 (cdr x))) (setf L2 (append L2 (list x))) ) ) L)
		(setf L L2)
	)
	(if (some #'null L)
		nil
		(progn (setf L (remove-duplicates (cdr L) :test #'equal))
		(setf L (remove-if-not #'(lambda(x) (if (eq x t) nil t))  L))
			(cond 
				((= (length L) 1) (car L))
				((zerop(length L)) t)
				(t (cons 'and L))
			)
		)
	)
)

(defun simplify-or (L)
	(let ((L2 nil))
		(mapcar #'(lambda(x) (if (and (listp x) (eq (car x) 'or)) (setf L2 (append L2 (cdr x))) (setf L2 (append L2 (list x))) ) ) L)
		(setf L L2)
	)
	(if (some #'(lambda(x) (eq x t)) L)
		t
		(progn (setf L (remove-duplicates (cdr L) :test #'equal))
		(setf L (remove-if-not #'(lambda(x) (if (eq x nil) nil t))  L))
			(cond
				((= (length L) 1) (car L))
				((zerop(length L)) nil)
				(t (cons 'or L))
			)
		)
	)
)

(defun simplify-not (L)
	(cond
		((eq (cadr L) t) nil)
		((eq (cadr L) nil) t)		
		((not(listp (cadr L))) L)
		((eq (caadr L) 'not) (cadadr L))
		((eq (caadr L) 'and) (cons 'or (mapcar #'(lambda(x) (cons 'not (cons x nil)) ) (cdadr L)  )  ) )
		((eq (caadr L) 'or) (cons 'and (mapcar #'(lambda(x) (cons 'not (cons x nil)) ) (cdadr L)  )  ) )
	)
)

(defun read-exprs-list()
	(let ((L nil))
		(with-open-file (in-file "D:\\College\\y4s2 CS427 - Programming Languages\\Lab\\Project\\simplify-boolean-expression-input.txt")
		    (do
				((line (read-line in-file nil) (read-line in-file nil)))
				((null line))
				(setf L (append L (list (string-expr->infix-list line))))
			)
		)
		L
	)
)

(defun save-expr(expr)
	(with-open-file (out-file "D:\\College\\y4s2 CS427 - Programming Languages\\Lab\\Project\\simplify-boolean-expression-output.txt"
						:direction :output
						:if-exists :append
						:if-does-not-exist :create
					)
			(print expr out-file)
	)
)

(defun simplify-boolean-expr (L)
	(when (listp L) (setf L (absorb L)))
	(cond
		((not (listp L)) L)
		((eq (car L) 'and) (simplify-and L))
		((eq (car L) 'or) (simplify-or L))
		((eq (car L) 'not) (simplify-not L))
	)
)

(defun simplify-expr(L)
	(setf L (simplify-boolean-expr L))
	(if 
		(not (listp L))
		L
		(mapcar #'(lambda(x) (if (listp x) (simplify-expr x) x) ) L)
	)
)

(defun simplify-list(L1 L2)
	(if (not (equal L1 L2))
		(simplify-list L2 (simplify-expr L2))
		L2
	)
)

(defun simplify()
	(mapcar #'(lambda (expr)  ( save-expr (simplify-list nil expr))    )  (read-exprs-list))
)


(eval '(simplify))
