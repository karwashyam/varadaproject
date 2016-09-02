<body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Booking Tool</span></a>
            </div>

            <div class="clearfix"></div>
            <!-- menu profile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>Admin</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->
            
            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>Menu</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-home"></i> Master <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/states">State</a></li>
                      <li><a href="${pageContext.request.contextPath}/city">City</a></li>
                      <li><a href="${pageContext.request.contextPath}/employee">Employee</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-table"></i> Projects <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/projects">List Projects</a></li>
                      <li><a href="${pageContext.request.contextPath}/project/add">Add Project</a></li>
                      <li><a href="${pageContext.request.contextPath}/payment-scheme">Payment Scheme</a></li>
                        <li><a href="${pageContext.request.contextPath}/proj-payment-scheme">Project Payment Scheme</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-users"></i> Franchisee <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/franchisee">List Franchisee</a></li>
                      <li><a href="${pageContext.request.contextPath}/add-franchisee">Add Franchisee</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-users"></i> Member <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/member">List Member</a></li>
                      <li><a href="${pageContext.request.contextPath}/add-member">Add Member</a></li>
                    </ul>
                  </li>
                   <li><a><i class="fa fa-edit"></i> Booking <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/booking">List Booking</a></li>
                      <li><a href="${pageContext.request.contextPath}/add-booking">Add Booking</a></li>
                    </ul>
                  </li> 
                  <li><a><i class="fa fa-dollar"></i> Payment <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/payment">Payment History</a></li>
                      <li><a href="${pageContext.request.contextPath}/add-payment">Add Payment</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-sitemap"></i> Others <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/franchisee-commission">Franchisee Commission</a></li>
                      <li><a href="${pageContext.request.contextPath}/add-franchisee-commission">Pay Franchisee Commission</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-bar-chart-o"></i> Reports <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath}/report/booking">Booking</a></li>
                      <li><a href="${pageContext.request.contextPath}/report/cancelledbooking">Cancelled Booking</a></li>
                      <li><a href="${pageContext.request.contextPath}/unbooked-plots-report">Unbooked Plots</a></li>
                      <li><a href="${pageContext.request.contextPath}/overdue-payments-report">Overdue Payments</a></li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
            <!-- /sidebar menu -->
            </div>
        </div>
        
        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right" style="margin-right:5px;">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="">Admin
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="${pageContext.request.contextPath}/change-password"> Change Password</a></li>
<!--                     <li> -->
<!--                       <a href="javascript:;"> -->
<!--                         <span class="badge bg-red pull-right">50%</span> -->
<!--                         <span>Settings</span> -->
<!--                       </a> -->
<!--                     </li> -->
<!--                     <li><a href="javascript:;">Help</a></li> -->
                    <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                  </ul>
                </li>

<!--                 <li role="presentation" class="dropdown"> -->
<!--                   <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false"> -->
<!--                     <i class="fa fa-envelope-o"></i> -->
<!--                     <span class="badge bg-green">6</span> -->
<!--                   </a> -->
<!--                   <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu"> -->
<!--                     <li> -->
<!--                       <a> -->
<%--                         <span class="image"><img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="Profile Image" /></span> --%>
<!--                         <span> -->
<!--                           <span>John Smith</span> -->
<!--                           <span class="time">3 mins ago</span> -->
<!--                         </span> -->
<!--                         <span class="message"> -->
<!--                           Film festivals used to be do-or-die moments for movie makers. They were where... -->
<!--                         </span> -->
<!--                       </a> -->
<!--                     </li> -->
<!--                     <li> -->
<!--                       <a> -->
<%--                         <span class="image"><img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="Profile Image" /></span> --%>
<!--                         <span> -->
<!--                           <span>John Smith</span> -->
<!--                           <span class="time">3 mins ago</span> -->
<!--                         </span> -->
<!--                         <span class="message"> -->
<!--                           Film festivals used to be do-or-die moments for movie makers. They were where... -->
<!--                         </span> -->
<!--                       </a> -->
<!--                     </li> -->
<!--                     <li> -->
<!--                       <a> -->
<%--                         <span class="image"><img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="Profile Image" /></span> --%>
<!--                         <span> -->
<!--                           <span>John Smith</span> -->
<!--                           <span class="time">3 mins ago</span> -->
<!--                         </span> -->
<!--                         <span class="message"> -->
<!--                           Film festivals used to be do-or-die moments for movie makers. They were where... -->
<!--                         </span> -->
<!--                       </a> -->
<!--                     </li> -->
<!--                     <li> -->
<!--                       <a> -->
<%--                         <span class="image"><img src="${pageContext.request.contextPath}/resources/assets/images/img.jpg" alt="Profile Image" /></span> --%>
<!--                         <span> -->
<!--                           <span>John Smith</span> -->
<!--                           <span class="time">3 mins ago</span> -->
<!--                         </span> -->
<!--                         <span class="message"> -->
<!--                           Film festivals used to be do-or-die moments for movie makers. They were where... -->
<!--                         </span> -->
<!--                       </a> -->
<!--                     </li> -->
<!--                     <li> -->
<!--                       <div class="text-center"> -->
<!--                         <a> -->
<!--                           <strong>See All Alerts</strong> -->
<!--                           <i class="fa fa-angle-right"></i> -->
<!--                         </a> -->
<!--                       </div> -->
<!--                     </li> -->
<!--                   </ul> -->
<!--                 </li> -->
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->