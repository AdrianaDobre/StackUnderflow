import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [MatFormFieldModule, MatButtonModule, NgIf, ReactiveFormsModule, MatIconModule, MatInputModule, RouterModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent {
  hide:boolean = true
  email = new FormControl('', [Validators.required, Validators.email]);
  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  confirmPassword = new FormControl('', [Validators.required]);

  constructor(private authService:AuthService, private router:Router, private _snackBar:MatSnackBar){}

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  formValid(){
    return this.password.value == this.confirmPassword.value && this.email.valid && this.username.valid;
  }

  attemptRegister(){
    if (this.formValid()){
      const userRegister = {
        email:this.email.value,
        password:this.password.value,
        username:this.username.value,
        retypePassword:this.confirmPassword.value
      }

      this.authService.proceedRegister(userRegister).subscribe({
        next: (r) => {
          localStorage.setItem('token', r.token)
          this.router.navigate(['/']).then(()=>window.location.reload())
        },
        error: (e) => {
          this._snackBar.open(e.error.message, "Dismiss", {
            duration:2000
          })
        }
      })
    }
    else{
      this._snackBar.open("Error, check all fields and try again", "Dismiss", {
        duration:2000
      })
    }
  }
}
