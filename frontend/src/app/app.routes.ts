import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { PostComponent } from './post/post.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomeComponent } from './home/home.component';
import { AddPostComponent } from './post/add-post/add-post.component';
import { SuggestionComponent } from './suggestion/suggestion.component';

export const routes: Routes = [
    {path: 'login', component: LoginFormComponent, pathMatch: 'full'},
    {path: 'register', component: RegisterFormComponent, pathMatch: 'full'},
    {path: 'view/:id', component: PostComponent},
    {path: 'question', component: AddPostComponent},
    {path: '', component: HomeComponent},
    {path: 'answer/:id', component: SuggestionComponent},
    //this should always be last
    {path: '**', component: NotFoundComponent}
];
