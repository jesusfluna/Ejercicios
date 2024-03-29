<script type="text/javascript">
$(window).load(function() {
    $('#exampleModalTextLink').modal('show');
});

  $('#myModal').on('shown.bs.modal', function () {
   $('#myInput').trigger('focus')
 })

</script>

<style>
	.top-bar {
	    background: #555;
	    color: #fff;
	    font-size: 0.9rem;
	    padding: 10px 0;
	}
	
	.top-bar .contact-info {
	    margin-right: 20px;
	}
	
	.top-bar ul {
	    margin-bottom: 0;
	}
	
	.top-bar .contact-info a {
	    font-size: 0.8rem;
	}
	
	.top-bar ul.social-custom {
	    margin-left: 20px;
	}
	.top-bar ul {
	    margin-bottom: 0;
	}
	
	.top-bar a.login-btn i, .top-bar a.signup-btn i {
	    margin-right: 10px;
	}
	
	.top-bar ul.social-custom a:hover {
	    background: #4fbfa8;
	    color: #fff;
	}
	.top-bar ul.social-custom a {
	    text-decoration: none !important;
	    font-size: 0.7rem;
	    width: 26px;
	    height: 26px;
	    line-height: 26px;
	    color: #999;
	    text-align: center;
	    border-radius: 50%;
	    margin: 0;
	}
	a:focus, a:hover {
	    color: #348e7b;
	    text-decoration: underline;
	}
	.top-bar a.login-btn, .top-bar a.signup-btn {
	    color: #eee;
	    text-transform: uppercase;
	    letter-spacing: 0.1em;
	    text-decoration: none !important;
	    font-size: 0.75rem;
	    font-weight: 700;
	    margin-right: 10px;
	}
	
	.fade.in {
  opacity: 1;
}
.modal.in .modal-dialog {
  -webkit-transform: translate(0, 0);
  -o-transform: translate(0, 0);
  transform: translate(0, 0);
}

.modal-backdrop .fade .in {
  opacity: 0.5 !important;
}


.modal-backdrop.fade {
    opacity: 0.5 !important;
}
	
</style>