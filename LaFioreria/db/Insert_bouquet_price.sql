
UPDATE la_fioreria.bouquet b
JOIN (
  SELECT
    br.bouquet_id,
    SUM(br.quantity * rf.unit_price) AS total_price
  FROM la_fioreria.bouquet_raw br
  JOIN la_fioreria.raw_flower rf
    ON br.raw_id = rf.raw_id
  GROUP BY br.bouquet_id
) AS t
  ON b.bouquet_id = t.bouquet_id
SET b.price = t.total_price;



  

