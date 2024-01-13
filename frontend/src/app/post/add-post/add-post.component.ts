import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { SelectTopicComponent } from './select-topic/select-topic.component';

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, SelectTopicComponent],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.scss'
})
export class AddPostComponent {
//POST post
  title!:string;
  body!:string;
  topics!:Array<any>;

  handleSelect($event:any){
    this.topics=$event;
  }
}
