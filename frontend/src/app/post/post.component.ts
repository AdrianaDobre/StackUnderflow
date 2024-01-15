import { Component, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from '../service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AnswerGridComponent } from '../answer-grid/answer-grid.component';
import { MatChipsModule } from '@angular/material/chips';
import { AddAnswerComponent } from '../answer/add-answer/add-answer.component';
import { AuthService } from '../service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, CommonModule, AnswerGridComponent, MatChipsModule, AddAnswerComponent],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit{
  @Input() postId!:string;
  title!:string;
  body!:string;
  tags?:Array<string>;
  bestAnswer?:string;
  votesByLoggedUser?:Array<any>;
  createdDate!:Date;
  userName!:string;
  userId!:string;

  isFullPost!:boolean;

  constructor(private postService:PostService, private route:ActivatedRoute, private router:Router, private authService:AuthService, private _snackBar:MatSnackBar){ }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('id')) this.isFullPost = true;
    const id = this.route.snapshot.paramMap.get('id') || this.postId;

    if (!id) { this.router.navigateByUrl('/404'); }

    this.postService.getPostById(id).subscribe({
      next: (r) => {
        this.title=r.title,
        this.body=r.body,
        this.tags=r.tags,
        this.bestAnswer=r.bestAnswer,
        this.votesByLoggedUser=r.votesByLoggedUser;
        this.createdDate=r.createdDate;
        this.userName=r.userName;
        this.userId=r.userId;
      },
        error: (e) => {
            this.router.navigateByUrl('/404')
          }
      })
  }

  goToPost(id: string) {
    this.router.navigateByUrl('/view/' + id)
  }

  deletePost() {
    this.postService.deletePost(this.postId).subscribe({
      next: (r) => {
        this._snackBar.open(r.message, "Dismiss", {
          duration:2000
        })
        setTimeout(function() {
          window.location.reload()
      }, 2000);
      }
})
  }

  getUserIdFromToken() { 
    return this.authService.getUserIdFromToken()
  }

  isLoggedIn() {
    return this.authService.isLoggedIn()
  }
}
