import { Component, OnInit } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [ MatToolbarModule, MatButtonModule, RouterModule, CommonModule ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  isLoggedIn:boolean = false;
  constructor(private authService:AuthService, private router:Router){}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn()
  }

  logOut(){
    this.authService.logOut()
    this.router.navigate(["/"]).then(() => window.location.reload())
  }
}
