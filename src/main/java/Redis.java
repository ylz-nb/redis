import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Redis {
    public static void main(String[] args){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Post post = new Post();
        post.setTitle("hash blog");
        post.setAuthor("ylz");
        post.setContent("my hash blog");
        Long postId = savePost(jedis, post);
        getPost(postId,jedis);
        System.out.println(post);
    }
    //保存
    static Long savePost(Jedis jedis, Post post) {
        Long postId = jedis.incr("posts");
        Map<String, String> myPost = new HashMap<String, String>();
        myPost.put("title", post.getTitle());
        myPost.put("content", post.getContent());
        myPost.put("author", post.getAuthor());
        jedis.hmset("post:" + postId, myPost);
        return postId;
    }
    //获取
    static Post getPost(Long postId, Jedis jedis){
        Map<String,String>myBlog=jedis.hgetAll("post:"+postId+":data");
        Post post=new Post();
        post.setTitle(myBlog.get("title"));
        post.setAuthor(myBlog.get("author"));
        post.setContent(myBlog.get("content"));

        return post;
    }

    static Long updateTitle(Long postId, Jedis jedis){
        Post post=getPost(postId,jedis);

        Map<String, String> myPost = new HashMap<String, String>();
        jedis.hmset("post:" + postId, myPost);
        return postId;
    }

}