import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { SelectTopicComponent } from './select-topic/select-topic.component';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../service/post.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, SelectTopicComponent, ReactiveFormsModule],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.scss'
})
export class AddPostComponent {
  title = new FormControl('', [Validators.required]);
  body = new FormControl('', [Validators.required]);
  topics!:Array<any>

  constructor (private postService: PostService, private router:Router, private _snackBar:MatSnackBar) {}

  handleSelect($event:any){
    this.topics=$event;
  }

  savePost(){
    console.log(this.title.valid)
    if (this.title.valid && this.body.valid){
      const post = {
        title:this.title.value,
        body:this.body.value,
        tags:this.topics
      }

      console.log(post)

      this.postService.createPost(post).subscribe({
        next: (r) => {
          this.router.navigate([`view/${r.message}`]).then(()=>window.location.reload())
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

  goToHome() {
    this.router.navigateByUrl('')
  }
}
