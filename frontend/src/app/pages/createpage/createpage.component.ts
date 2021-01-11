import { Component, OnInit, ViewChild } from "@angular/core";
import { MemeService } from "../../services/meme.service";
import { Meme } from "../../models/Meme";
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from "../../services/auth.service";
import { TagService } from "app/services/tag.service";
import { Tag } from "app/models/Tag";
import { CategoryService } from "app/services/category.service";
import { Category } from "app/models/Category";

let self: any;

@Component({
  selector: "ngx-createpage",
  templateUrl: "./createpage.component.html",
  styleUrls: ["./createpage.component.scss"],
})
export class CreatepageComponent implements OnInit {

  tagOptions: string[];
  categoryOptions: string[];
  tagFilteredOptions$: Observable<string[]>;
  categoryFilteredOptions$: Observable<string[]>;
  tag: Tag;

  @ViewChild('tagInput') tagInput;
  @ViewChild('categoryInput') categoryInput;

  constructor(
    private memeService: MemeService,
    private authService: AuthService,
    private tagService: TagService,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    self = this;

    this.fillCanvas();
    this.tagService.getTags().pipe(
      tap((result)=>console.log(result))
    ).subscribe((tags: Tag[])=>{
        this.tag = {title: "test"};
        this.tagOptions = [];
        tags.forEach(tag => this.tagOptions.push(tag.title))
        this.tagFilteredOptions$ = of(this.tagOptions);
        this.activateTagButton(this.tag, this.tagService, tags);
      }
      
    );
    this.categoryService.getCategories().pipe(
      tap((result)=>console.log(result))
    ).subscribe((categories: Category[])=>{
      this.categoryOptions = [];
      categories.forEach(category => this.categoryOptions.push(category.name))
      this.categoryFilteredOptions$ = of(this.categoryOptions);
      this.activateCategoryButton(this.categoryService);
    })
  }

  activateTagButton(tag: Tag, tagService: TagService, tags: Tag[]){
    const tagbutton = document.querySelector('.add-tag-button');
        tagbutton.addEventListener("click", function(event){
          let tagInput = <HTMLInputElement>document.getElementById('tag');
          tag.title = tagInput.value;
          let add = true;
          tags.forEach(oldTag => {
            if(tag.title == oldTag.title){
              add = false;
            }
          })
          if(add){
            tagService.createTag(tag).subscribe((data: any)=> console.log(data));
          }
        }, false)
  }

  activateCategoryButton(categoryService: CategoryService){
    const categorybutton = document.querySelector('.add-category-button');
        categorybutton.addEventListener("click", function(event){
          let categoryInput = <HTMLInputElement>document.getElementById('category');
          // Now the category id needs to be given to the new Meme
        }, false)
  }

  private tagFilter(value: string): string[] {
    const tagFilterValue = value.toLowerCase();
    return this.tagOptions.filter(tagOptionValue => tagOptionValue.toLowerCase().includes(tagFilterValue));
  }

  private categoryFilter(value: string): string[] {
    const categoryFilterValue = value.toLowerCase();
    return this.categoryOptions.filter(categoryOptionValue => categoryOptionValue.toLowerCase().includes(categoryFilterValue));
  }

  getTagFilteredOptions(value: string): Observable<string[]> {
    return of(value).pipe(
      map(filterString => this.tagFilter(filterString)),
    );
  }

  getCategoryFilteredOptions(value: string): Observable<string[]> {
    return of(value).pipe(
      map(filterString => this.categoryFilter(filterString)),
    );
  }

  tagOnChange() {
    this.tagFilteredOptions$ = this.getTagFilteredOptions(this.tagInput.nativeElement.value);
  }

  categoryOnChange() {
    this.categoryFilteredOptions$ = this.getCategoryFilteredOptions(this.categoryInput.nativeElement.value);
  }

  tagOnSelectionChange($event){
    this.tagFilteredOptions$ = this.getTagFilteredOptions($event);
  }

  categoryOnSelectionChange($event){
    this.categoryFilteredOptions$ = this.getCategoryFilteredOptions($event);
  }

  handleSaveImage(e) {
    var canvas = document.querySelector("canvas");
    console.log(canvas);
    var ctx = canvas.getContext("2d");
    var reader = new FileReader();
    reader.onload = function (event) {
      var img = new Image();
      img.onload = function () {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);
      };
      img.src = event.target.result as string;
      console.log("img src:  ");
      var temp = event.target.result as string;
      var image = document.querySelector("canvas");
      canvas.setAttribute("src", img.src);
    };

    reader.readAsDataURL(e.target.files[0]);
  }

  fillCanvas() {
    var inputField = document.querySelector("input");
    var canvas = document.querySelector("canvas");
    var ctx = canvas.getContext("2d");
    inputField.addEventListener("keyup", function () {
      var image = document.querySelector("canvas");
      var temp = canvas.getAttribute("src");
      canvas.width = image.width;
      canvas.setAttribute("crossOrigin", "Anonymous");
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0);
      ctx.font = "64px Verdana";
      //redraw image
      ctx.clearRect(0, 0, image.width, image.height);

      var tempImg = new Image();
      tempImg.src = temp;
      ctx.drawImage(tempImg, 0, 0);
      // canvas.setAttribute("background-image", temp);
      //refill text
      ctx.fillStyle = "black";
      ctx.fillText(inputField.value, 40, 80);
    });

    var imageLoader = document.getElementById("imageLoader");
    imageLoader.addEventListener("change", this.handleSaveImage, false);

    var savebutton = document.querySelector(".save-button");
    savebutton.addEventListener("click", this.saveMeme);
  }

  saveMeme() {
    var canvas = document.querySelector("canvas");

    const userId = this.authService.getCurrentUser().id;

    canvas.toBlob(function (blob) {
      var newImg = document.createElement("img"),
        url = URL.createObjectURL(blob);

      var meme: Meme = {
        title: "test",
        categoryId: 1,
        userId: userId,
        imageblob: blob,
      };

      self.memeService.CreateMeme(meme).subscribe((res) => {
        console.log(res);
      });

      newImg.onload = function () {
        // no longer need to read the blob so it's revoked
        URL.revokeObjectURL(url);

        newImg.src = url;
        document.body.appendChild(newImg);
      };
    });
  }
}
