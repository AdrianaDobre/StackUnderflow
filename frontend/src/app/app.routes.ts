import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { PostComponent } from './post/post.component';

export const routes: Routes = [
    {path: 'login', component: LoginFormComponent, pathMatch: 'full'},
    {path: 'register', component: RegisterFormComponent, pathMatch: 'full'},
    {path: 'view/:id', component: PostComponent},
];
