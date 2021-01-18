import {Component, OnInit} from '@angular/core';
import { Meme } from 'app/models/Meme';
import { MemeService } from 'app/services/meme.service';
import { DomSanitizer } from '@angular/platform-browser';
import { WebSocketAPI } from './WebSocketAPI';
import { ActivatedRoute, Params, Router } from '@angular/router';

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

class ResponseClass {
  memeId: string;
  isUpvote: boolean;

  constructor(jsonStr: string) {
    let jsonObj: any = JSON.parse(jsonStr);
    for (let prop in jsonObj) {
        this[prop] = jsonObj[prop];
    }
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
  webSocketAPI: WebSocketAPI;
  items = []
  loading = false;

  constructor(public memeService: MemeService, private sanitizer : DomSanitizer, private route: ActivatedRoute){}

  loadNext(){
    if(this.loading) {return}
    this.loading = true;
    // this.memeService.GetAllMemes().subscribe(meme => this.items.push(...meme));
    this.loading = false;
  }

  
  ngOnInit(){
    self = this;
    this.webSocketAPI = new WebSocketAPI(this);
    this.connect();
    this.route.params.forEach((params: Params) => {
      let category = +params['category']; // (+) converts string 'id' to a number
      if(category != null){
        this.memeService.GetAllMemesFilteredOnCategory(category);
      }
      else{
        this.memeService.GetAllMemes().subscribe(meme => this.inserItem(meme));
      } 
  });
  }

  ngOnDestroy(){
    this.disconnect();
  }

  sendMessage(voteType, item){
    let response;
    if(voteType === "u"){
      response = {memeId: item.id,isUpvote: true}
    }else{
      response = {memeId: item.id,isUpvote: false}
    }

    this.webSocketAPI._send(response);
  }

  connect(){
    this.webSocketAPI._connect();
  }

  disconnect(){
    this.webSocketAPI._disconnect();
  }

  inserItem(meme: Meme[]){
    meme.forEach(function(element){
      element.imageSrc = 'data:image/png;base64,' + element.imageblob;

      self.items.push(element)
    })
  }

  handleMessage(message){
    let fObj: ResponseClass = new ResponseClass(message);
    var item2 = self.items.filter(function(item) {
      return item.id === fObj.memeId;
    })[0];

    if(fObj.isUpvote){
      item2.likes++;
    }else{
      item2.dislikes++;
    }
    //this.items = message;
  }
}
