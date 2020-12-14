import {Component, OnInit} from '@angular/core';
import { Meme } from 'app/shared/models/Meme';
import { MemeService } from 'app/_helpers/MemeService';
import { DomSanitizer } from '@angular/platform-browser';

//TODO: move this to a generic model folder
class Card{
  id: number;
  title: string;
  image: string;
  upvotes: number;
  downvotes: number;

  constructor(title: string, image: string, id: number){
    this.title = title;
    this.image = image;
    this.id = id
    this.upvotes = 0;
    this.downvotes = 0;
  }
}

let self: any;

@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})


export class DashboardComponent implements OnInit{
  
  // items = getPlaceholderCards();
  items = []
  loading = false;

  constructor(public memeService: MemeService, private sanitizer : DomSanitizer){}

  loadNext(){
    if(this.loading) {return}
    this.loading = true;
    // this.memeService.GetAllMemes().subscribe(meme => this.items.push(...meme));
    this.loading = false;
  }
  ngOnInit(){
    self = this;

    this.memeService.GetAllMemes().subscribe(meme => this.inserItem(meme));
  }

  inserItem(meme: Meme[]){
    meme.forEach(function(element){
      element.imageSrc = 'data:image/png;base64,' + element.imageblob;

      self.items.push(element)
    })
  }
}
