import { Component } from '@angular/core';
import { PostComponent } from '../post/post.component';
import { PostService } from '../service/post.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatTooltipModule} from '@angular/material/tooltip';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [PostComponent, CommonModule, MatIconModule, MatButtonModule, MatTooltipModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  postIds?:Array<string>;

  constructor(private postService:PostService, private router: Router) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (r) => {
        this.postIds = r.map((post: { id: any; }) => post.id).reverse()
      }})
  }

  goToQuestionCreate() {
    this.router.navigateByUrl('/question');
  }
}
