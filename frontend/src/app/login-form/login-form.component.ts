import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [MatFormFieldModule, MatButtonModule, NgIf, ReactiveFormsModule, MatIconModule, MatInputModule, RouterModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {
  hide:boolean = true
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required])

  constructor(private authService:AuthService, private router:Router, private _snackBar:MatSnackBar){}

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  attemptLogin(){
    const user = {
      email: this.email.value,
      password: this.password.value
    }

    this.authService.proceedLogin(user).subscribe({
      next: (r) => {
        if (r) {
          localStorage.setItem('token', r.token)
          this.router.navigate(['/']).then(()=>window.location.reload())
        }
      },
        error: (e) => {
          this._snackBar.open(e.error.message, "Dismiss", {
            duration:2000
          })
        }
      })

  }
}
